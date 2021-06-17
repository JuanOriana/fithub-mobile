package com.example.fithub_mobile;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import edu.cmu.pocketsphinx.Hypothesis;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fithub_mobile.backend.models.FullCycleExercise;
import com.example.fithub_mobile.excercise.ExerciseData;
import com.example.fithub_mobile.repository.Resource;
import com.example.fithub_mobile.repository.Status;
import com.example.fithub_mobile.ui.search.FilterDialogFragment;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

public class ExecutionActivity extends AppCompatActivity implements RecognitionListener {

    private ArrayList<FullCycleExercise> exercises = new ArrayList<>();
    private ProgressBar pgBar;
    private ExerciseQueueRealState exerciseQueueRealState;
    private CountDownTimer cTimer;
    private long millisLeft = 0;
    /* We only need the keyphrase to start recognition, one menu with list of choices,
      and one word that is required for method switchSearch - it will bring recognizer
      back to listening for the keyphrase*/
    private static final String KWS_SEARCH = "wakeup";
    private static final String MENU_SEARCH = "menu";
    /* Keyword we are looking for to activate recognition */
    private static final String KEYPHRASE = "fithub";

    /* Recognition object */
    private SpeechRecognizer recognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execution);
        runRecognizerSetup();


        exerciseQueueRealState = ExerciseQueueRealState.getInstance();

        pgBar = findViewById(R.id.progressBar);
        pgBar.setProgress(0);

        setPrevExercise();
        setNextExercise();

        ImageButton nextBtn = findViewById(R.id.next);
        nextBtn.setOnClickListener(view -> setNextExercise());
        ImageButton prevBtn = findViewById(R.id.prev);
        prevBtn.setOnClickListener(view -> setPrevExercise());
        ToggleButton playBtn = findViewById(R.id.play_btn);
        playBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (millisLeft == 0) return;
                if (isChecked && cTimer != null){
                    cTimer.cancel();
                }
                else{
                    cTimer = new CountDownTimer(millisLeft, 1000) {
                        @SuppressLint("SetTextI18n")
                        public void onTick(long millisUntilFinished) {
                            ((TextView)findViewById(R.id.execution_seconds)).setText(Integer.toString((int)millisUntilFinished / 1000));
                            millisLeft = (int)millisUntilFinished;
                        }

                        public void onFinish() {
                            setNextExercise();
                        }

                    }.start();
                }
            }
        });
    }
    private void runRecognizerSetup() {
        // Recognizer initialization is a time-consuming and it involves IO,
        // so we execute it in async task
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(ExecutionActivity.this);
                    File assetDir = assets.syncAssets();
                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }
            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                    System.out.println(result.getMessage());
                } else {
                    switchSearch(KWS_SEARCH);
                }
            }
        }.execute();
    }

    private void setupRecognizer(File assetsDir) throws IOException {
        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))
                // Disable this line if you don't want recognizer to save raw
                // audio files to app's storage
                .setRawLogDir(assetsDir)
                .getRecognizer();
        recognizer.addListener(this);
        // Create keyword-activation search.
        recognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);
        // Create your custom grammar-based search
        File menuGrammar = new File(assetsDir, "mymenu.gram");
        recognizer.addGrammarSearch(MENU_SEARCH, menuGrammar);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (recognizer != null) {
            recognizer.cancel();
            recognizer.shutdown();
        }
    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null)
            return;
        String text = hypothesis.getHypstr();
        if (text.equals(KEYPHRASE))
            switchSearch(MENU_SEARCH);
        else if(text.equals("next")) {
            System.out.println(hypothesis.getHypstr());
        }
        else if(text.equals("start")) {
        }
        else if(text.equals("stop")){

        }
        else
            System.out.println(hypothesis.getHypstr());
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        if (hypothesis != null) {
            System.out.println(hypothesis.getHypstr());
        }
    }

    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onEndOfSpeech() {
        if (!recognizer.getSearchName().equals(KWS_SEARCH))
            switchSearch(KWS_SEARCH);
    }

    private void switchSearch(String searchName) {
        recognizer.stop();
        if (searchName.equals(KWS_SEARCH))
            recognizer.startListening(searchName);
        else
            recognizer.startListening(searchName, 10000);
    }

    @Override
    public void onError(Exception error) {
        System.out.println(error.getMessage());
    }

    @Override
    public void onTimeout() {
        switchSearch(KWS_SEARCH);
    }


    private void setNextExercise(){
        if (exerciseQueueRealState.setNextExercise() == -1) {
            Toast.makeText(getApplicationContext(),getText(R.string.success_routine),Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        setCurrentInfo(exerciseQueueRealState.getCurrentExercise());
        updateProgress();
    }


    private void setPrevExercise(){
        if (exerciseQueueRealState.setPrevExercise() == -1)
            return;


        if (exerciseQueueRealState.getCurrentExercise() != null) {
            setCurrentInfo(exerciseQueueRealState.getCurrentExercise());
            updateProgress();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setCurrentInfo(FullCycleExercise currentExercise){
        if (cTimer != null){
            cTimer.cancel();
        }
        View current = this.findViewById(R.id.exercise_execution);

        findViewById(R.id.execution_seconds).setVisibility(View.VISIBLE);
        findViewById(R.id.execution_seconds_title).setVisibility(View.VISIBLE);
        findViewById(R.id.execution_rep_title).setVisibility(View.VISIBLE);
        findViewById(R.id.execution_reps).setVisibility(View.VISIBLE);

        TextView currentText = current.findViewById(R.id.execution_title);
        currentText.setText(currentExercise.getExercise().getName());
        currentText = current.findViewById(R.id.execution_desc);
        currentText.setText(currentExercise.getExercise().getDetail());

        final TextView  secondsView = current.findViewById(R.id.execution_seconds);
        ToggleButton playBtn = findViewById(R.id.play_btn);
        playBtn.setChecked(false);
        int exerciseVal = currentExercise.getDuration();
        if(exerciseVal <= 0) {
            playBtn.setVisibility(View.GONE);
            secondsView.setVisibility(View.GONE);
            current.findViewById(R.id.execution_seconds_title).setVisibility(View.GONE);
        } else {
            playBtn.setVisibility(View.VISIBLE);
            secondsView.setText(Integer.toString(exerciseVal));

            millisLeft = exerciseVal*1000;
            cTimer = new CountDownTimer(millisLeft, 1000) {
                public void onTick(long millisUntilFinished) {
                    secondsView.setText(Integer.toString((int)millisUntilFinished / 1000));
                    millisLeft = (int)millisUntilFinished;
                }

                public void onFinish() {
                    setNextExercise();
                }

            }.start();

        }

        currentText = current.findViewById(R.id.execution_reps);
        exerciseVal = currentExercise.getRepetitions();
        if(exerciseVal <= 0) {
            currentText.setVisibility(View.INVISIBLE);
            current.findViewById(R.id.execution_rep_title).setVisibility(View.INVISIBLE);
        } else {
            currentText.setText(Integer.toString(exerciseVal));
        }

        ImageView currentImage = current.findViewById(R.id.execution_img);
        App app = (App)getApplication();
        app.getExerciseImageRepository().getExerciseImages(currentExercise.getExercise().getId()).observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                String url = r.getData().getContent().get(0).getUrl();
                Picasso.get().load(url).into(currentImage);
            } else {
                Resource.defaultResourceHandler(r);
            }
        });


    }

    private void updateProgress(){
        if (pgBar == null)
            return;

        pgBar.setProgress((int)(exerciseQueueRealState.ratio()*100));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.execution_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.changeExecView:
                Intent i = new Intent(this, ExecutionQueueActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}