package com.example.fithub_mobile.excercise;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.example.fithub_mobile.R;
import com.google.android.material.card.MaterialCardView;


public class LastlyExecutedExerciseCard extends MaterialCardView {

    public LastlyExecutedExerciseCard(Context context, String title, String description) {
        super(context);
        inflate(getContext(), R.layout.lastly_executed_exercise,this);

        TextView titleView = findViewById(R.id.lastly_exec_exercise_title);
        titleView.setText(title);
        TextView descView = findViewById(R.id.lastly_exec_exercise_desc);
        descView.setText(description);
    }

}
