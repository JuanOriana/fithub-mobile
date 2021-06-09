package com.example.fithub_mobile.routine;

import android.content.Intent;
import android.os.Bundle;

import com.example.fithub_mobile.CycleData;
import com.example.fithub_mobile.CycleDisplay;
import com.example.fithub_mobile.ExecutionActivity;
import com.example.fithub_mobile.NotificationActivity;
import com.example.fithub_mobile.QrGenActivity;
import com.example.fithub_mobile.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageButton;
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
        if (title!=null)
            toolBarLayout.setTitle(title);

        RatingBar ratingBar = findViewById(R.id.rating_bar_routine_view);
        ratingBar.setRating(rating);

        TextView descView = findViewById(R.id.desc_routine);
        if (desc != null)
            descView.setText(desc);

        LinearLayout cycleContainer = findViewById(R.id.cycle_container);
        cycleContainer.addView(new CycleDisplay(this,new CycleData(1,"Uno",4)));
        cycleContainer.addView(new CycleDisplay(this,new CycleData(1,"Uno",4)));
        cycleContainer.addView(new CycleDisplay(this,new CycleData(1,"Uno",4)));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startExecution());

        ImageButton qrBtn = findViewById(R.id.qr_btn);
        qrBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, QrGenActivity.class);
            startActivity(i);
        });

        ImageButton calendarBtn = findViewById(R.id.calendar_btn);
        calendarBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, NotificationActivity.class);
            startActivity(i);

        });
    }

    public void startExecution(){
        Intent i = new Intent(this, ExecutionActivity.class);
        startActivity(i);
    }
}