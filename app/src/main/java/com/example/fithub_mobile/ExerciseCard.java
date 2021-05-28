package com.example.fithub_mobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

@SuppressLint("ViewConstructor")
public class ExerciseCard extends MaterialCardView {

    public ExerciseCard(Context context, String title, String description, int repeats, int seconds) {
        super(context);
        inflate(getContext(),R.layout.routine_card,this);

        TextView titleView = findViewById(R.id.title_exercise);
        titleView.setText(title);
        TextView descView = findViewById(R.id.desc_exercise);
        titleView.setText(description);
        TextView repeatsView = findViewById(R.id.repeats_exercise);
        repeatsView.setText(repeats);
        TextView secondsView = findViewById(R.id.seconds_exercise);
        secondsView.setText(seconds);
    }

}
