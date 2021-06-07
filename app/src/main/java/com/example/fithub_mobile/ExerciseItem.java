package com.example.fithub_mobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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