package com.example.fithub_mobile;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class RoutineCard extends MaterialCardView {

    public RoutineCard(Context context, String title) {
        super(context);
        inflate(getContext(),R.layout.routine_card,this);
        TextView text = findViewById(R.id.title_card);
        text.setText(title);

        MaterialButton routineBtn = findViewById(R.id.routine_btn);
        routineBtn.setOnClickListener(view -> {
            Intent i = new Intent(getContext(),RoutineActivity.class);
            getContext().startActivity(i);
        });
    }

}
