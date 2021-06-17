package com.example.fithub_mobile.ui.excercise;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;

import com.example.fithub_mobile.R;
import com.google.android.material.card.MaterialCardView;


public class LastlyExecutedCard extends MaterialCardView {

    public LastlyExecutedCard(Context context,Integer id,  String title, String description) {
        super(context);
        inflate(getContext(), R.layout.lastly_executed_exercise,this);

        TextView titleView = findViewById(R.id.lastly_exec_exercise_title);
        titleView.setText(title);
        TextView descView = findViewById(R.id.lastly_exec_exercise_desc);
        descView.setText(description);

        setOnClickListener(v->{
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://fithub.com/routine?id=" + id));
            i.setPackage("com.example.fithub_mobile");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            getContext().startActivity(i);
        });
    }

}
