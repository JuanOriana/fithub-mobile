package com.example.fithub_mobile.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fithub_mobile.Login;
import com.example.fithub_mobile.R;
import com.example.fithub_mobile.RoutineCard;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private LinearLayout cardContainer;
    SharedPreferences sp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        sp = getContext().getSharedPreferences("login",getContext().MODE_PRIVATE);

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView username = root.findViewById(R.id.userName);
        username.setText(sp.getString("username", "User"));

        cardContainer = root.findViewById(R.id.cardContainer);

        cardContainer.addView(new RoutineCard(getActivity(),"Uno","Una descripcion random",5));
        cardContainer.addView(new RoutineCard(getActivity(),"Dos","Una descripcion random",1));
        cardContainer.addView(new RoutineCard(getActivity(),"Tres","Una descripcion random",3));

        return root;
    }

    public void goToLogin(){
        Intent i = new Intent(getContext(), Login.class);
        startActivity(i);
        getActivity().finish();
    }

    public void logOut(View view){
        sp.edit().putBoolean("logged",false).apply();
        goToLogin();
    }
}