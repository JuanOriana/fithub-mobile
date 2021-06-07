package com.example.fithub_mobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.fithub_mobile.R;

public class CycleDisplay extends ConstraintLayout {
    public CycleDisplay(Context context) {
        super(context);
        inflate(getContext(), R.layout.cycle_display,this);
    }

    @SuppressLint("SetTextI18n")
    public CycleDisplay(Context context, CycleData cycleData) {
        super(context);
        inflate(getContext(),R.layout.cycle_display,this);
        TextView nameView = findViewById(R.id.cycle_name);
        nameView.setText(cycleData.getTitle());
        TextView setView = findViewById(R.id.set_count);
        setView.setText(cycleData.getReps().toString());

        LinearLayout exerciseContainer = findViewById(R.id.exercise_container);
        exerciseContainer.addView(new ExerciseItem(getContext(),new ExerciseData(1,"Uno", "",4,6,"")));
        exerciseContainer.addView(new ExerciseItem(getContext(),new ExerciseData(1,"Uno", "",4,6,"")));
        exerciseContainer.addView(new ExerciseItem(getContext(),new ExerciseData(1,"Uno", "",4,6,"")));
    }
}
