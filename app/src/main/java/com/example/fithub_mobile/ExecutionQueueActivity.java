package com.example.fithub_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.fithub_mobile.ui.home.HomeViewModel;

import java.util.ArrayList;

public class ExecutionQueueActivity extends AppCompatActivity {

    ArrayList<ExerciseData> exercises = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execution_queue);

        exercises.add(new ExerciseData(1,"Diácono","Prueba",4,4,"http://i.imgur.com/DvpvklR.png"));
        exercises.add(new ExerciseData(1,"Diácono","Prueba",4,4,"http://i.imgur.com/DvpvklR.png"));
        exercises.add(new ExerciseData(1,"Diácono","Prueba",4,4,"http://i.imgur.com/DvpvklR.png"));

        RecyclerView exerciseContainer = findViewById(R.id.exercise_container);
        exerciseContainer.setLayoutManager(new LinearLayoutManager(this));
        ExerciseAdapter adapter = new ExerciseAdapter(exercises);
        exerciseContainer.setLayoutManager(new LinearLayoutManager(this));
        exerciseContainer.setAdapter(adapter);

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