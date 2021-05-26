package com.example.fithub_mobile;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class RoutineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        Intent intent = getIntent();
        String title = intent.getStringExtra(RoutineCard.TITLE_MESSAGE);
        int rating = intent.getIntExtra(RoutineCard.RATING_MESSAGE,0);
        String desc = intent.getStringExtra(RoutineCard.DESC_MESSAGE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(title);

        RatingBar ratingBar = findViewById(R.id.rating_bar_routine_view);
        ratingBar.setRating(rating);

        TextView descView = findViewById(R.id.desc_routine);
        descView.setText(desc);

        LinearLayout cycleContainer = findViewById(R.id.cycle_container);
        cycleContainer.addView(new CycleDisplay(this,"Uno",4));
        cycleContainer.addView(new CycleDisplay(this,"Dos",4));
        cycleContainer.addView(new CycleDisplay(this,"Tres",3));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startExecution());
        Button startBtn = findViewById(R.id.start_routine);
        startBtn.setOnClickListener(view -> startExecution());
    }

    public void startExecution(){
        Intent i = new Intent(this,ExecutionActivity.class);
        startActivity(i);
    }
}