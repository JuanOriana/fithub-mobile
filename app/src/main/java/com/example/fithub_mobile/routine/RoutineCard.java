package com.example.fithub_mobile.routine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.lifecycle.LifecycleOwner;

import com.example.fithub_mobile.App;
import com.example.fithub_mobile.R;
import com.example.fithub_mobile.backend.models.FullRoutine;
import com.example.fithub_mobile.repository.Resource;
import com.example.fithub_mobile.repository.Status;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

@SuppressLint("ViewConstructor")
public class RoutineCard extends MaterialCardView {

    public static String TITLE_MESSAGE = "com.example.fithub_mobile.NAME";
    public static final String RATING_MESSAGE = "com.example.fithub_mobile.RATING";
    public static final String DESC_MESSAGE = "com.example.fithub_mobile.DESC";
    public static final String SHARE_MESSAGE = "com.example.fithub_mobile.SHARE";
    public String title;


    public RoutineCard(Context context, FullRoutine routine) {
        super(context);
        inflate(getContext(), R.layout.routine_card,this);

        TextView titleView = findViewById(R.id.title_card);
        titleView.setText(routine.getName());
        title = routine.getName();
        TextView descView = findViewById(R.id.desc_card);
        descView.setText(routine.getDetail());
        RatingBar ratingBar = findViewById(R.id.rating_bar);
        ratingBar.setRating(routine.getAverageRating());
        TextView userNameView = findViewById(R.id.owner_name);
        userNameView.setText(routine.getUser().getUsername());
        ToggleButton favButton = findViewById(R.id.fav_button);
        ImageView userImgView = findViewById(R.id.owner_img);
        TextView difficulty = findViewById(R.id.difficulty);
        Picasso.get().load(routine.getUser().getAvatarUrl()).into(userImgView);
        String text;
        switch(routine.getDifficulty()) {
            case "rookie":
                text = context.getString(R.string.rookie_difficulty);
                break;
            case "beginner":
                text = context.getString(R.string.beginner_difficulty);
                break;
            case "intermediate":
                text = context.getString(R.string.intermediate_difficulty);
                break;
            case "advanced":
                text = context.getString(R.string.advanced_difficulty);
                break;
            case "expert":
                text = context.getString(R.string.expert_difficulty);
                break;
            default:
                text = "";
                break;
        }

        difficulty.setText(text);


        ImageButton shareBtn = findViewById(R.id.share_btn);
        shareBtn.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.fithub);
            String shareMessage = "\n" + context.getString(R.string.share_msg) + "\n\n";
            shareMessage = shareMessage + "http://fithub.com/routine?id=" + routine.getId()+ "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(shareIntent, "Choose one"));
        });

        MaterialButton routineBtn = findViewById(R.id.routine_btn);
        routineBtn.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://fithub.com/routine?id=" + routine.getId()));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.setPackage("com.example.fithub_mobile");
            context.startActivity(i);
        });

        App app = (App)context.getApplicationContext();
        app.getFavouriteRepository().getFavourites().observe((LifecycleOwner) context, rfav -> {
            if (rfav.getStatus() == Status.SUCCESS) {
                assert rfav.getData() != null;
                favButton.setOnCheckedChangeListener(null);
                if (rfav.getData().getContent().contains(routine)) {
                    favButton.toggle();
                }
                favButton.setOnCheckedChangeListener((v,isChecked)->{
                    if (isChecked){
                        app.getFavouriteRepository().addFavourite(routine.getId()).observe((LifecycleOwner) context, r -> {
                            if (r.getStatus() == Status.SUCCESS) {
                                return;
                            } else {
                                Resource.defaultResourceHandler(r);
                            }
                        });
                    }else{
                        app.getFavouriteRepository().deleteFavourite(routine.getId()).observe((LifecycleOwner) context, r -> {
                            if (r.getStatus() == Status.SUCCESS) {
                                return;
                            } else {
                                Resource.defaultResourceHandler(r);
                            }
                        });
                    }
                });
            } else {
                Resource.defaultResourceHandler(rfav);
            }
        });



    }

    public String getTitle() {
        return title;
    }
}
