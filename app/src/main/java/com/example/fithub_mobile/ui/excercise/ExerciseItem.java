package com.example.fithub_mobile.ui.excercise;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.fithub_mobile.R;
import com.example.fithub_mobile.backend.models.FullCycleExercise;

@SuppressLint("ViewConstructor")
public class ExerciseItem extends ConstraintLayout {



    @SuppressLint("SetTextI18n")
    public ExerciseItem(Context context, FullCycleExercise exerciseData) {
        super(context);

        inflate(getContext(), R.layout.exercise_item, this);

        findViewById(R.id.item_secs_title).setVisibility(View.VISIBLE);
        findViewById(R.id.item_reps_title).setVisibility(View.VISIBLE);
        findViewById(R.id.secs_count).setVisibility(View.VISIBLE);
        findViewById(R.id.reps_count).setVisibility(View.VISIBLE);


        TextView titleView = findViewById(R.id.exercise_name);
        titleView.setText(exerciseData.getExercise().getName());

        TextView secs = findViewById(R.id.secs_count);
        int val = exerciseData.getDuration();
        if(val <= 0) {
            secs.setVisibility(View.INVISIBLE);
            findViewById(R.id.item_secs_title).setVisibility(View.INVISIBLE);
        } else {
            secs.setText(Integer.toString(val));
        }

        TextView reps = findViewById(R.id.reps_count);
        val = exerciseData.getRepetitions();
        if(val <= 0) {
            findViewById(R.id.item_reps_title).setVisibility(View.GONE);
            reps.setVisibility(View.GONE);
        } else {
            reps.setText(Integer.toString(val));
        }

    }
}