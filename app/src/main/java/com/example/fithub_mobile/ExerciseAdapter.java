package com.example.fithub_mobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
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

import com.example.fithub_mobile.excercise.ExerciseData;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collection;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private ArrayList<ExerciseData> exercises;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView descView;
        private final TextView repCount;
        private final TextView secsCount;
        private final ImageView image;

        public ViewHolder(View view) {
            super(view);
            titleView = view.findViewById(R.id.title_exercise);
            descView = view.findViewById(R.id.desc_exercise);
            repCount = view.findViewById(R.id.repeats_exercise);
            secsCount = view.findViewById(R.id.seconds_exercise);
            image = view.findViewById(R.id.img_exercise);


        }

        public TextView getTitleView() {
            return titleView;
        }

        public TextView getDescView() {
            return descView;
        }

        public TextView getRepCount() {
            return repCount;
        }

        public TextView getSecsCount() {
            return secsCount;
        }

        public ImageView getImage() {
            return image;
        }
    }


    public ExerciseAdapter(ArrayList<ExerciseData> exercises){

        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_card, parent, false);

        return new ExerciseAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ExerciseAdapter.ViewHolder holder, int position) {
        holder.getTitleView().setText(exercises.get(position).getTitle());
        holder.getDescView().setText(exercises.get(position).getDesc());
        holder.getRepCount().setText(exercises.get(position).getReps().toString());
        holder.getSecsCount().setText(exercises.get(position).getSecs().toString());
      //  Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(holder.getImage());

    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }
}

