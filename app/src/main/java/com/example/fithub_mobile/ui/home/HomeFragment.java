package com.example.fithub_mobile.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithub_mobile.App;
import com.example.fithub_mobile.R;
import com.example.fithub_mobile.backend.models.FullRoutine;
import com.example.fithub_mobile.backend.models.PublicUser;
import com.example.fithub_mobile.excercise.LastlyExecutedCard;
import com.example.fithub_mobile.excercise.LastlyExecutedCardData;
import com.example.fithub_mobile.excercise.LastlyExecutedCardDataManager;
import com.example.fithub_mobile.repository.Resource;
import com.example.fithub_mobile.repository.Status;
import com.example.fithub_mobile.routine.RoutineCardAdapter;
import com.example.fithub_mobile.routine.RoutineCardData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView cardContainer;
    private LinearLayout recentContainer;
    private RoutineCardAdapter adapter;
    private ArrayList<FullRoutine> routines = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recentContainer = root.findViewById(R.id.recent_container);

        LastlyExecutedCardDataManager lastlyExecManager = LastlyExecutedCardDataManager.getInstance();

        if (lastlyExecManager.getData(root.getContext()).size() == 0){
            TextView noDataText = new TextView(root.getContext());
            noDataText.setText("NO HAY EJERCICIOS");
            recentContainer.addView(noDataText);
        }
        else {
            for (LastlyExecutedCardData item : lastlyExecManager.getData(root.getContext()))
                recentContainer.addView(new LastlyExecutedCard(root.getContext(),item.getId(), item.getTitle(), item.getDescription()));
        }


        cardContainer = root.findViewById(R.id.cardContainer);
        adapter = new RoutineCardAdapter(routines);
        cardContainer.setLayoutManager(new GridLayoutManager(getContext(),getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 2));
        cardContainer.setAdapter(adapter);

        initRoutines();
        return root;
    }


    public void initRoutines() {
        App app = (App) getActivity().getApplication();
        app.getRoutineRepository().getRoutines().observe(getViewLifecycleOwner(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                assert r.getData() != null;
                routines.addAll(r.getData().getContent());

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