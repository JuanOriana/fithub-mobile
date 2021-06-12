package com.example.fithub_mobile.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fithub_mobile.App;
import com.example.fithub_mobile.Login;
import com.example.fithub_mobile.R;
import com.example.fithub_mobile.backend.models.Error;
import com.example.fithub_mobile.backend.models.FullRoutine;
import com.example.fithub_mobile.backend.models.FullUser;
import com.example.fithub_mobile.backend.models.PublicUser;
import com.example.fithub_mobile.repository.Resource;
import com.example.fithub_mobile.repository.Status;
import com.example.fithub_mobile.routine.RoutineCard;
import com.example.fithub_mobile.routine.RoutineCardAdapter;
import com.example.fithub_mobile.routine.RoutineCardData;
import com.example.fithub_mobile.routine.RoutineCardAdapter;
import com.example.fithub_mobile.routine.RoutineCardData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private RecyclerView cardContainer;
    private ArrayList<FullRoutine> routines = new ArrayList<>();
    private View root;
    private FullUser user;
    SharedPreferences sp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        root = inflater.inflate(R.layout.fragment_profile, container, false);

        getUserData();

        Button editButton = root.findViewById(R.id.edit_btn);
        editButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_navigation_profile_to_navigation_editprofile));


        PublicUser user = new PublicUser(1,"fede","f","https://ep00.epimg.net/elcomidista/imagenes/2020/09/02/articulo/1599041159_343586_1599041590_rrss_normal.jpg",3,3);
        FullRoutine routine = new FullRoutine(1,"Hola","hola",13213123,4,true,"Advanced",user,null,null);

        routines.add(routine);
        routines.add(routine);
        routines.add(routine);
        routines.add(routine);

        cardContainer = root.findViewById(R.id.cardContainer);
        RoutineCardAdapter adapter = new RoutineCardAdapter(routines);
        cardContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        cardContainer.setAdapter(adapter);

        return root;
    }

    public void goToLogin() {
        Intent i = new Intent(getContext(), Login.class);
        startActivity(i);
        requireActivity().finish();
    }

    public void getUserData() {
        App app = (App) getActivity().getApplication();
        app.getUserRepository().getCurrentUser().observe(getViewLifecycleOwner(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                Log.d("LOGIN", "Funciono");
                user = r.getData();
                String name = user.getFirstName() + " " + user.getLastName();
                TextView username = root.findViewById(R.id.userName);
                username.setText(name);
                ImageView userImg = root.findViewById(R.id.userImg);
                Picasso.get().load(user.getAvatarUrl()).into(userImg);
            } else {
                Resource.defaultResourceHandler(r);
            }
        });
    }
}