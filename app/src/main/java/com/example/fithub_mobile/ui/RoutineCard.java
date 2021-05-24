package com.example.fithub_mobile.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fithub_mobile.R;

public class RoutineCard extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.routine_card, container, false);

        TextView textView = root.findViewById(R.id.title_card);
        textView.setText("Hola");

        return root;
    }
}
