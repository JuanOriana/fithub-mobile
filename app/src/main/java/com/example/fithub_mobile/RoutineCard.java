package com.example.fithub_mobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

@SuppressLint("ViewConstructor")
public class RoutineCard extends MaterialCardView {

    public static final String TITLE_MESSAGE = "com.example.fithub_mobile.NAME";


    public RoutineCard(Context context, String title, String desc, Integer rating) {
        super(context);
        inflate(getContext(),R.layout.routine_card,this);

        TextView titleView = findViewById(R.id.title_card);
        titleView.setText(title);
        TextView descView = findViewById(R.id.desc_card);
        descView.setText(desc);
        RatingBar ratingBar = findViewById(R.id.rating_bar);
        ratingBar.setRating(rating);

        MaterialButton routineBtn = findViewById(R.id.routine_btn);
        routineBtn.setOnClickListener(view -> {
            Intent i = new Intent(getContext(),RoutineActivity.class);
            i.putExtra(TITLE_MESSAGE, title);
            getContext().startActivity(i);
        });
    }

}
