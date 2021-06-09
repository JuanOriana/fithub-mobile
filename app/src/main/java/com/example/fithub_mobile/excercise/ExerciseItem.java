package com.example.fithub_mobile.excercise;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.fithub_mobile.R;

@SuppressLint("ViewConstructor")
public class ExerciseItem extends ConstraintLayout {



    @SuppressLint("SetTextI18n")
    public ExerciseItem(Context context, ExerciseData exerciseData) {
        super(context);
        inflate(getContext(), R.layout.exercise_item, this);

        TextView titleView = findViewById(R.id.exercise_name);
        titleView.setText(exerciseData.getTitle());
        TextView reps = findViewById(R.id.reps_count);
        reps.setText(exerciseData.getReps().toString());
        TextView secs = findViewById(R.id.secs_count);
        secs.setText(exerciseData.getSecs().toString());


    }
}