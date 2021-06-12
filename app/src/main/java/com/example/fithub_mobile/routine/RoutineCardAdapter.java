package com.example.fithub_mobile.routine;

import android.content.Context;
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
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithub_mobile.R;
import com.example.fithub_mobile.backend.models.FullRoutine;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

public class RoutineCardAdapter extends RecyclerView.Adapter<RoutineCardAdapter.ViewHolder> implements Filterable {

    private ArrayList<FullRoutine> routines;
    private ArrayList<FullRoutine> allRoutines;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView descView;
        private final RatingBar ratingBar;
        private final ImageButton shareBtn;
        private final MaterialButton routineBtn;
        private final TextView userNameView;
        private final ImageView userImgView;
        private final TextView difficulty;
        private final ToggleButton favButton;
        private Context context;

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
            context = view.getContext();
            favButton = view.findViewById(R.id.fav_button);

        }

        public ToggleButton getFavButton() {
            return favButton;
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

        public Context getContext() {
            return context;
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter  = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<FullRoutine> filteredList = new ArrayList<>();
            if(constraint.toString().isEmpty())
                filteredList.addAll(allRoutines);
            else {
                for(FullRoutine routine : allRoutines){
                    if(routine.getName().toLowerCase().contains(constraint.toString().toLowerCase()))
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
            routines.addAll((Collection<? extends FullRoutine>) results.values);
            notifyDataSetChanged();
        }
    };

    public RoutineCardAdapter(ArrayList<FullRoutine> routines){

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
        holder.getTitleView().setText(routines.get(position).getName());
        holder.getDescView().setText(routines.get(position).getDetail());
        holder.getRatingBar().setRating(routines.get(position).getAverageRating());
        holder.getUserNameView().setText(routines.get(position).getUser().getUsername());
        holder.getFavButton().setChecked(routines.get(position).isFavourite());
        Picasso.get().load(routines.get(position).getUser().getAvatarUrl()).into(holder.getUserImgView());
//        String text;
//        switch(routines.get(position).getDifficulty()) {
//            case RoutineCardData.EASY_DIFFICULTY:
//                text = holder.itemView.getContext().getString(R.string.easy_difficulty);
//                break;
//            case RoutineCardData.MEDIUM_DIFFICULTY:
//                text = holder.itemView.getContext().getString(R.string.medium_difficulty);
//                break;
//            case RoutineCardData.HARD_DIFFICULTY:
//                text = holder.itemView.getContext().getString(R.string.hard_difficulty);
//                break;
//            default:
//                text = "";
//        }
        holder.getDifficulty().setText(routines.get(position).getDifficulty());

        //Sharing
        ImageButton shareBtn = holder.getShareBtn();
        shareBtn.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.fithub);
            String shareMessage = "\n" + holder.getContext().getString(R.string.share_msg) + "\n\n";
            shareMessage = shareMessage + "http://fithub.com/routine?id=" + routines.get(position).getId()+ "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            holder.getContext().startActivity(Intent.createChooser(shareIntent, "Choose one"));
        });

        //Going to routine
        MaterialButton routineBtn = holder.getRoutineBtn();
        routineBtn.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://fithub.com/routine?id=" + routines.get(position).getId()));
            i.setPackage("com.example.fithub_mobile");
//            i.putExtra(TITLE_MESSAGE, title);
//            i.putExtra(RATING_MESSAGE, rating);
//            i.putExtra(DESC_MESSAGE, desc);
            holder.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return routines.size();
    }
}
