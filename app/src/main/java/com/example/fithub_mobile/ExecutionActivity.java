package com.example.fithub_mobile;


import android.annotation.SuppressLint;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;

import com.example.fithub_mobile.backend.models.FullCycleExercise;
import com.example.fithub_mobile.excercise.ExerciseData;
import com.example.fithub_mobile.repository.Resource;
import com.example.fithub_mobile.repository.Status;
import com.example.fithub_mobile.ui.search.FilterDialogFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExecutionActivity extends AppCompatActivity {

    private ArrayList<FullCycleExercise> exercises = new ArrayList<>();
    private ProgressBar pgBar;
    private ExerciseQueueRealState exerciseQueueRealState;
    private CountDownTimer cTimer;
    private long millisLeft = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execution);


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