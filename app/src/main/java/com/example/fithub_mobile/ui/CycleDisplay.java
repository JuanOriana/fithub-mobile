package com.example.fithub_mobile.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;

import com.example.fithub_mobile.App;
import com.example.fithub_mobile.R;
import com.example.fithub_mobile.backend.models.FullCycle;
import com.example.fithub_mobile.backend.models.FullCycleExercise;
import com.example.fithub_mobile.ui.excercise.ExerciseItem;
import com.example.fithub_mobile.repository.Resource;
import com.example.fithub_mobile.repository.Status;

import java.util.List;

public class CycleDisplay extends ConstraintLayout {
    public CycleDisplay(Context context) {
        super(context);
        inflate(getContext(), R.layout.cycle_display,this);
    }

    @SuppressLint("SetTextI18n")
    public CycleDisplay(Context context, FullCycle cycleData) {
        super(context);
        inflate(getContext(),R.layout.cycle_display,this);
        TextView nameView = findViewById(R.id.cycle_name);
        nameView.setText(cycleData.getName());
        TextView setView = findViewById(R.id.set_count);
        setView.setText(Integer.toString(cycleData.getRepetitions()));

        LinearLayout exerciseContainer = findViewById(R.id.exercise_container);

        App app = (App)context.getApplicationContext();
        app.getCycleRepository().getCycleExercises(cycleData.getId()).observe((LifecycleOwner) getContext(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                List<FullCycleExercise> exercises = r.getData().getContent();
                for (FullCycleExercise exercise : exercises) {
                    exerciseContainer.addView(new ExerciseItem(getContext(),exercise));
                }
            } else {
                Resource.defaultResourceHandler(r);
            }
        });
    }
}
