package com.example.fithub_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fithub_mobile.backend.models.FullCycleExercise;
import com.example.fithub_mobile.excercise.ExerciseData;
import com.example.fithub_mobile.repository.Resource;
import com.example.fithub_mobile.repository.Status;
import com.example.fithub_mobile.ui.home.HomeViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExecutionQueueActivity extends AppCompatActivity {

    private ArrayList<FullCycleExercise> exercises = new ArrayList<>();
    private ExerciseAdapter adapter;
    private ProgressBar pgBar;
    private ExerciseQueueRealState exerciseQueueRealState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execution_queue);

        exerciseQueueRealState = ExerciseQueueRealState.getInstance();


        RecyclerView exerciseContainer = findViewById(R.id.exercise_container);
        exerciseContainer.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExerciseAdapter(exerciseQueueRealState.getExercises());
        exerciseContainer.setAdapter(adapter);

        pgBar = findViewById(R.id.progressBarQueue);
        pgBar.setProgress(0);

        setCurrentInfo(exerciseQueueRealState.getCurrentExercise());
        updateProgress();

        ImageButton nextBtn = findViewById(R.id.next_queue);
        nextBtn.setOnClickListener(view -> setNextExercise());
        ImageButton prevBtn = findViewById(R.id.prev_queue);
        prevBtn.setOnClickListener(view -> setPrevExercise());

    }

    private void setNextExercise(){
        if (exerciseQueueRealState.setNextExercise() == -1) {
            Toast.makeText(getApplicationContext(), getText(R.string.success_routine), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        setCurrentInfo(exerciseQueueRealState.getCurrentExercise());
        adapter.notifyDataSetChanged();
        updateProgress();
    }


    private void setPrevExercise(){
        if (exerciseQueueRealState.setPrevExercise() == -1)
            return;

        setCurrentInfo(exerciseQueueRealState.getCurrentExercise());
        adapter.notifyDataSetChanged();
        updateProgress();
    }

    @SuppressLint("SetTextI18n")
    private void setCurrentInfo(FullCycleExercise currentExercise){
        View current = this.findViewById(R.id.current_exercise_card);

        findViewById(R.id.queue_seconds).setVisibility(View.VISIBLE);
        findViewById(R.id.queue_seconds_title).setVisibility(View.VISIBLE);
        findViewById(R.id.queue_rep_title).setVisibility(View.VISIBLE);
        findViewById(R.id.queue_repetitions).setVisibility(View.VISIBLE);

        TextView currentText = current.findViewById(R.id.current_title);
        currentText.setText(currentExercise.getExercise().getName());
        currentText = current.findViewById(R.id.current_description);
        currentText.setText(currentExercise.getExercise().getDetail());

        currentText = current.findViewById(R.id.queue_seconds);
        int seconds = currentExercise.getDuration();
        if(seconds <= 0) {
            currentText.setVisibility(View.GONE);
            current.findViewById(R.id.queue_seconds_title).setVisibility(View.GONE);
        } else {
            currentText.setText(Integer.toString(seconds));
        }
        currentText = current.findViewById(R.id.queue_repetitions);
        int reps = currentExercise.getRepetitions();
        if(reps <= 0) {
            currentText.setVisibility(View.INVISIBLE);
            current.findViewById(R.id.queue_rep_title).setVisibility(View.INVISIBLE);
        } else {
            currentText.setText(Integer.toString(reps));
        }
        ImageView currentImage = current.findViewById(R.id.current_image);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.changeExecView:
                Intent i = new Intent(this, ExecutionActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}