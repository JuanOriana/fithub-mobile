package com.example.fithub_mobile.routine;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithub_mobile.R;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

public class RoutineCardAdapter extends RecyclerView.Adapter<RoutineCardAdapter.ViewHolder> implements Filterable {

    private ArrayList<RoutineCardData> routines;
    private ArrayList<RoutineCardData> allRoutines;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView descView;
        private final RatingBar ratingBar;
        private final ImageButton shareBtn;
        private final MaterialButton routineBtn;
        private final TextView userNameView;
        private final ImageView userImgView;
        private final TextView difficulty;

        public ViewHolder(View view) {
            super(view);
            titleView = view.findViewById(R.id.title_card);
            descView = view.findViewById(R.id.desc_card);
            ratingBar = view.findViewById(R.id.rating_bar);
            shareBtn = view.findViewById(R.id.share_btn);
            routineBtn = view.findViewById(R.id.routine_btn);
            userNameView = view.findViewById(R.id.owner_name);
            userImgView = view.findViewById(R.id.owner_img);
            difficulty = view.findViewById(R.id.difficulty);

            //Sharing
            ImageButton shareBtn = view.findViewById(R.id.share_btn);
            shareBtn.setOnClickListener(v -> {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.fithub);
                String shareMessage = "\n" + view.getContext().getString(R.string.share_msg) + "\n\n";
                shareMessage = shareMessage + "http://fithub.com/routine" + "\n\n";
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

        public TextView getUserNameView() {
            return userNameView;
        }

        public ImageView getUserImgView() {
            return userImgView;
        }

        public TextView getDifficulty() {
            return difficulty;
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter  = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<RoutineCardData> filteredList = new ArrayList<>();
            if(constraint.toString().isEmpty())
                filteredList.addAll(allRoutines);
            else {
                for(RoutineCardData routine : allRoutines){
                    if(routine.getTitle().toLowerCase().contains(constraint.toString().toLowerCase()))
                        filteredList.add(routine);
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            routines.clear();
            routines.addAll((Collection<? extends RoutineCardData>) results.values);
            notifyDataSetChanged();
        }
    };

    public RoutineCardAdapter(ArrayList<RoutineCardData> routines){

        this.routines = routines;
        this.allRoutines = new ArrayList<>(routines);
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
        holder.getUserNameView().setText(routines.get(position).getUserName());
        Picasso.get().load(routines.get(position).getUserImg()).into(holder.getUserImgView());
        String text;
        switch(routines.get(position).getDifficulty()) {
            case RoutineCardData.EASY_DIFFICULTY:
                text = holder.itemView.getContext().getString(R.string.easy_difficulty);
                break;
            case RoutineCardData.MEDIUM_DIFFICULTY:
                text = holder.itemView.getContext().getString(R.string.medium_difficulty);
                break;
            case RoutineCardData.HARD_DIFFICULTY:
                text = holder.itemView.getContext().getString(R.string.hard_difficulty);
                break;
            default:
                text = "";
        }
        holder.getDifficulty().setText(text);
    }

    @Override
    public int getItemCount() {
        return routines.size();
    }
}
