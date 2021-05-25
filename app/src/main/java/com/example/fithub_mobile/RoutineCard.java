package com.example.fithub_mobile;

import android.content.Context;

import com.google.android.material.card.MaterialCardView;

public class RoutineCard extends MaterialCardView {


    public RoutineCard(Context context) {
        super(context);
        inflate(getContext(),R.layout.routine_card,this);
    }

}
