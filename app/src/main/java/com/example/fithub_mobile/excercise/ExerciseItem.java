package com.example.fithub_mobile.excercise;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.fithub_mobile.R;
import com.example.fithub_mobile.backend.models.FullCycleExercise;
import com.example.fithub_mobile.backend.models.FullExercise;

@SuppressLint("ViewConstructor")
public class ExerciseItem extends ConstraintLayout {



    @SuppressLint("SetTextI18n")
    public ExerciseItem(Context context, FullCycleExercise exerciseData) {
        super(context);
        inflate(getContext(), R.layout.exercise_item, this);

        TextView titleView = findViewById(R.id.exercise_name);
        titleView.setText(exerciseData.getExercise().getName());
        TextView reps = findViewById(R.id.reps_count);
        reps.setText(Integer.toString(exerciseData.getRepetitions()));
        TextView secs = findViewById(R.id.secs_count);
        secs.setText(Integer.toString(exerciseData.getDuration()));

    }
}