package com.example.fithub_mobile;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithub_mobile.excercise.ExerciseData;
import com.example.fithub_mobile.ui.search.FilterDialogFragment;
import com.squareup.picasso.Picasso;

public class ExecutionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execution);

        ExerciseData currentExercise = new ExerciseData(1,"Diácono","Prueba",4,4,"http://i.imgur.com/DvpvklR.png");

        setCurrentInfo(currentExercise);
    }

    private void setCurrentInfo(ExerciseData currentExercise){
        View current = this.findViewById(R.id.exercise_execution);

        TextView currentText = current.findViewById(R.id.execution_title);
        currentText.setText(currentExercise.getTitle());
        currentText = current.findViewById(R.id.execution_desc);
        currentText.setText(currentExercise.getDesc());
        currentText = current.findViewById(R.id.execution_seconds);
        currentText.setText(currentExercise.getSecs().toString());
        currentText = current.findViewById(R.id.execution_reps);
        currentText.setText(currentExercise.getReps().toString());

        ImageView currentImage = current.findViewById(R.id.execution_img);
        Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(currentImage);
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