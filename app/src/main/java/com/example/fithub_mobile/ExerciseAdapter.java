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

import com.example.fithub_mobile.backend.models.FullCycleExercise;
import com.example.fithub_mobile.excercise.ExerciseData;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private ArrayList<FullCycleExercise> exercises;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView descView;
        private final TextView repCount;
        private final TextView secsCount;
        private final TextView secondsTitle;
        private final TextView repsTitle;
        private final ImageView image;


        public ViewHolder(View view) {
            super(view);

            titleView = view.findViewById(R.id.title_exercise);
            descView = view.findViewById(R.id.desc_exercise);
            secsCount = view.findViewById(R.id.seconds_exercise);
            secondsTitle = view.findViewById(R.id.exercise_card_seconds_title);
            repsTitle = view.findViewById(R.id.exercise_card_reps_title);
            repCount = view.findViewById(R.id.repeats_exercise);
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

        public TextView getSecondsTitle() {
            return secondsTitle;
        }

        public TextView getRepsTitle() {
            return repsTitle;
        }
    }


    public ExerciseAdapter(ArrayList<FullCycleExercise> exercises){

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

        holder.getSecsCount().setVisibility(View.VISIBLE);
        holder.getSecondsTitle().setVisibility(View.VISIBLE);
        holder.getRepsTitle().setVisibility(View.VISIBLE);
        holder.getRepCount().setVisibility(View.VISIBLE);

        holder.getTitleView().setText(exercises.get(position).getExercise().getName());
        holder.getDescView().setText(exercises.get(position).getExercise().getDetail());

        int seconds = exercises.get(position).getDuration();
        if(seconds <= 0) {
            holder.getSecsCount().setVisibility(View.GONE);
            holder.getSecondsTitle().setVisibility(View.GONE);
        } else {
            holder.getSecsCount().setText(Integer.toString(seconds));
        }

        int reps = exercises.get(position).getRepetitions();
        if(reps <= 0) {
            holder.getRepCount().setVisibility(View.INVISIBLE);
            holder.getRepsTitle().setVisibility(View.INVISIBLE);
        } else {
            holder.getRepCount().setText(Integer.toString(reps));
        }
        Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(holder.getImage());
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }
}

