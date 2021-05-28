package com.example.fithub_mobile.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fithub_mobile.R;
import com.example.fithub_mobile.RoutineCard;



public class EditProfileFragment extends Fragment {

    private EditProfileViewModel editProfileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        editProfileViewModel =  new ViewModelProvider(this).get(EditProfileViewModel.class);

        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }
}