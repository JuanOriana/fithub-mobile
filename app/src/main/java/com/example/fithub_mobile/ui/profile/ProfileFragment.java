package com.example.fithub_mobile.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fithub_mobile.App;
import com.example.fithub_mobile.Login;
import com.example.fithub_mobile.R;
import com.example.fithub_mobile.backend.models.FullRoutine;
import com.example.fithub_mobile.backend.models.FullUser;
import com.example.fithub_mobile.backend.models.PublicUser;
import com.example.fithub_mobile.repository.Resource;
import com.example.fithub_mobile.repository.Status;
import com.example.fithub_mobile.routine.RoutineCardAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private RecyclerView cardContainer;
    private ArrayList<FullRoutine> routines = new ArrayList<>();
    private View root;
    private FullUser user;
    RoutineCardAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        root = inflater.inflate(R.layout.fragment_profile, container, false);

        Button editButton = root.findViewById(R.id.edit_btn);
        editButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_navigation_profile_to_navigation_editprofile));

        cardContainer = root.findViewById(R.id.cardContainer);
        adapter = new RoutineCardAdapter(routines);
        cardContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        cardContainer.setAdapter(adapter);

        getUserData();

        return root;
    }

    public void goToLogin() {
        Intent i = new Intent(getContext(), Login.class);
        startActivity(i);
        requireActivity().finish();
    }

    public void getUserData() {
        App app = (App) getActivity().getApplication();
        AtomicReference<String> name = new AtomicReference<>();
        AtomicReference<String> userImg = new AtomicReference<>();
        app.getUserRepository().getCurrentUser().observe(getViewLifecycleOwner(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                user = r.getData();
                name.set(user.getFirstName() + " " + user.getLastName());
                TextView username = root.findViewById(R.id.userName);
                username.setText(name.get());
                ImageView userImgView = root.findViewById(R.id.userImg);
                userImg.set(user.getAvatarUrl());
                Picasso.get().load(userImg.get()).into(userImgView);
            } else {
                Resource.defaultResourceHandler(r);
            }
        });

        app.getUserRepository().getUserRoutines().observe(getViewLifecycleOwner(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                assert r.getData() != null;
                TextView routineCount = root.findViewById(R.id.routine_count_val);
                routineCount.setText(r.getData().getTotalCount().toString());
                routines.addAll(r.getData().getContent());
                for (FullRoutine routine : routines){
                    routine.setUser(new PublicUser(1,name.get(),null,userImg.get(),0,0));
                }

                app.getFavouriteRepository().getFavourites().observe(getViewLifecycleOwner(), rfav -> {
                    if (rfav.getStatus() == Status.SUCCESS) {
                        assert rfav.getData() != null;
                        for (FullRoutine routine : routines){
                            if (rfav.getData().getContent().contains(routine)) {
                                routine.setFavourite(true);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    } else {
                        Resource.defaultResourceHandler(rfav);
                    }
                });


                adapter.notifyDataSetChanged();
            } else {
                Resource.defaultResourceHandler(r);
            }
        });

    }


}