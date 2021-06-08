package com.example.fithub_mobile.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithub_mobile.R;
import com.example.fithub_mobile.routine.RoutineCardAdapter;
import com.example.fithub_mobile.routine.RoutineCardData;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    private ArrayList<RoutineCardData> routines = new ArrayList<>();
    private FavoritesViewModel favoritesViewModel;
    private RecyclerView cardContainer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritesViewModel =
                new ViewModelProvider(this).get(FavoritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);

        cardContainer = root.findViewById(R.id.cardContainer);

        routines.add(new RoutineCardData(1,"Titulo","Prueba",4));
        routines.add(new RoutineCardData(2,"Titulo","Prueba",5));
        routines.add(new RoutineCardData(3,"Titulo","Prueba",1));

        cardContainer = root.findViewById(R.id.cardContainer);
        RoutineCardAdapter adapter = new RoutineCardAdapter(routines);
        cardContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        cardContainer.setAdapter(adapter);

        return root;
    }
}