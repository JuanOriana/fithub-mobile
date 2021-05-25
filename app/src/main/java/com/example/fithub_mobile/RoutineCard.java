package com.example.fithub_mobile;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

public class RoutineCard extends MaterialCardView {

    public RoutineCard(Context context, String title) {
        super(context);
        inflate(getContext(),R.layout.routine_card,this);
        TextView text = findViewById(R.id.title_card);
        text.setText(title);
    }

}
