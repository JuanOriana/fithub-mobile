package com.example.fithub_mobile;

import android.content.Context;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.fithub_mobile.R;

public class CycleDisplay extends ConstraintLayout {
    public CycleDisplay(Context context) {
        super(context);
        inflate(getContext(), R.layout.cycle_display,this);
    }

    public CycleDisplay(Context context,String name, Integer sets) {
        super(context);
        inflate(getContext(),R.layout.cycle_display,this);
        TextView nameView = findViewById(R.id.cycle_name);
        nameView.setText(name);
        TextView setView = findViewById(R.id.set_count);
        setView.setText(sets.toString());
    }
}
