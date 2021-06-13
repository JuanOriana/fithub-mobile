package com.example.fithub_mobile;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithub_mobile.backend.models.FullCycleExercise;
import com.example.fithub_mobile.excercise.ExerciseData;
import com.example.fithub_mobile.ui.search.FilterDialogFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExecutionActivity extends AppCompatActivity {

    private ArrayList<FullCycleExercise> exercises = new ArrayList<>();
    private ProgressBar pgBar;
    private ExerciseQueueRealState exerciseQueueRealState;

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
    }


    private void setNextExercise(){
        if (exerciseQueueRealState.setNextExercise() == -1) {
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
        View current = this.findViewById(R.id.exercise_execution);

        TextView currentText = current.findViewById(R.id.execution_title);
        currentText.setText(currentExercise.getExercise().getName());
        currentText = current.findViewById(R.id.execution_desc);
        currentText.setText(currentExercise.getExercise().getDetail());
        currentText = current.findViewById(R.id.execution_seconds);
        currentText.setText(Integer.toString(currentExercise.getDuration()));
        currentText = current.findViewById(R.id.execution_reps);
        currentText.setText(Integer.toString(currentExercise.getRepetitions()));

        ImageView currentImage = current.findViewById(R.id.execution_img);
        Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(currentImage);
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
                Intent i = new Intent(this, ExecutionQueueActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}