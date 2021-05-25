package com.example.fithub_mobile.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.fithub_mobile.R;
import com.example.fithub_mobile.RoutineCard;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private LinearLayout cardContainer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);


        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        cardContainer = root.findViewById(R.id.cardContainer);

        cardContainer.addView(new RoutineCard(getActivity(),"Uno"));
        cardContainer.addView(new RoutineCard(getActivity(),"Dos"));
        cardContainer.addView(new RoutineCard(getActivity(),"Tres"));

        return root;
    }
}