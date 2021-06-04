package com.example.fithub_mobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

@SuppressLint("ViewConstructor")
public class RoutineCard extends MaterialCardView {

    public static String TITLE_MESSAGE = "com.example.fithub_mobile.NAME";
    public static final String RATING_MESSAGE = "com.example.fithub_mobile.RATING";
    public static final String DESC_MESSAGE = "com.example.fithub_mobile.DESC";
    public static final String SHARE_MESSAGE = "com.example.fithub_mobile.SHARE";
    public String title;


    public RoutineCard(Context context, RoutineCardData routine) {
        super(context);
        inflate(getContext(),R.layout.routine_card,this);

        TextView titleView = findViewById(R.id.title_card);
        titleView.setText(routine.getTitle());
        title = routine.getTitle();
        TextView descView = findViewById(R.id.desc_card);
        descView.setText(routine.getDesc());
        RatingBar ratingBar = findViewById(R.id.rating_bar);
        ratingBar.setRating(routine.getRating());

        ImageButton shareBtn = findViewById(R.id.share_btn);
        shareBtn.setOnClickListener(view -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.fithub);
            String shareMessage= "\n"+ getContext().getString(R.string.share_msg)+"\n\n";
            shareMessage = shareMessage + "http://fithub.com/routine" +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            getContext().startActivity(Intent.createChooser(shareIntent, "Choose one"));
        });

        MaterialButton routineBtn = findViewById(R.id.routine_btn);
        routineBtn.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://fithub.com/routine"));
            i.setPackage("com.example.fithub_mobile");
//            i.putExtra(TITLE_MESSAGE, title);
//            i.putExtra(RATING_MESSAGE, rating);
//            i.putExtra(DESC_MESSAGE, desc);
            getContext().startActivity(i);
        });

    }

    public String getTitle() {
        return title;
    }
}
