package com.example.fithub_mobile;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RoutineCardAdapter extends RecyclerView.Adapter<RoutineCardAdapter.ViewHolder> {

    private ArrayList<RoutineCardData> routines;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView descView;
        private final RatingBar ratingBar;
        private final ImageButton shareBtn;
        private final MaterialButton routineBtn;

        public ViewHolder(View view) {
            super(view);
            titleView = view.findViewById(R.id.title_card);
            descView = view.findViewById(R.id.desc_card);
            ratingBar = view.findViewById(R.id.rating_bar);
            shareBtn = view.findViewById(R.id.share_btn);
            routineBtn = view.findViewById(R.id.routine_btn);

            //Sharing
            ImageButton shareBtn = view.findViewById(R.id.share_btn);
            shareBtn.setOnClickListener(v -> {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.fithub);
                String shareMessage= "\n"+ view.getContext().getString(R.string.share_msg)+"\n\n";
                shareMessage = shareMessage + "http://fithub.com/routine" +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                view.getContext().startActivity(Intent.createChooser(shareIntent, "Choose one"));
            });

            //Going to routine
            MaterialButton routineBtn = view.findViewById(R.id.routine_btn);
            routineBtn.setOnClickListener(v -> {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://fithub.com/routine"));
                i.setPackage("com.example.fithub_mobile");
//            i.putExtra(TITLE_MESSAGE, title);
//            i.putExtra(RATING_MESSAGE, rating);
//            i.putExtra(DESC_MESSAGE, desc);
                view.getContext().startActivity(i);
            });
        }

        public TextView getTitleView() {
            return titleView;
        }

        public TextView getDescView() {
            return descView;
        }

        public RatingBar getRatingBar() {
            return ratingBar;
        }

        public ImageButton getShareBtn() {
            return shareBtn;
        }

        public MaterialButton getRoutineBtn() {
            return routineBtn;
        }
    }

    public RoutineCardAdapter(ArrayList<RoutineCardData> routines){
        this.routines = routines;
    }

    @NonNull
    @Override
    public RoutineCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.routine_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineCardAdapter.ViewHolder holder, int position) {
        holder.getTitleView().setText(routines.get(position).getTitle());
        holder.getDescView().setText(routines.get(position).getDesc());
        holder.getRatingBar().setRating(routines.get(position).getRating());

    }

    @Override
    public int getItemCount() {
        return routines.size();
    }
}
