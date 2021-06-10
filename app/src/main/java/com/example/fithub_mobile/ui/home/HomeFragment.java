package com.example.fithub_mobile.ui.home;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithub_mobile.R;
import com.example.fithub_mobile.excercise.LastlyExecutedExerciseCard;
import com.example.fithub_mobile.routine.RoutineCardAdapter;
import com.example.fithub_mobile.routine.RoutineCardData;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView cardContainer;
    private LinearLayout recentContainer;
    private ArrayList<RoutineCardData> routines = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recentContainer = root.findViewById(R.id.recent_container);
        recentContainer.addView(new LastlyExecutedExerciseCard(root.getContext(),"Hola","Que tal"));
        recentContainer.addView(new LastlyExecutedExerciseCard(root.getContext(),"Hola","Que tal"));
        recentContainer.addView(new LastlyExecutedExerciseCard(root.getContext(),"Hola","Que tal"));
        recentContainer.addView(new LastlyExecutedExerciseCard(root.getContext(),"Hola","Que tal"));



        routines.add(new RoutineCardData(1,"Titulo","Prueba",4,
                "pollo","https://ep00.epimg.net/elcomidista/imagenes/2020/09/02/articulo/1599041159_343586_1599041590_rrss_normal.jpg", RoutineCardData.HARD_DIFFICULTY));
        routines.add(new RoutineCardData(2,"Titulo","Prueba",5,
                "pollo","https://ep00.epimg.net/elcomidista/imagenes/2020/09/02/articulo/1599041159_343586_1599041590_rrss_normal.jpg", RoutineCardData.HARD_DIFFICULTY));
        routines.add(new RoutineCardData(3,"Titulo","Prueba",1,
                "pollo","https://ep00.epimg.net/elcomidista/imagenes/2020/09/02/articulo/1599041159_343586_1599041590_rrss_normal.jpg", RoutineCardData.HARD_DIFFICULTY));

        cardContainer = root.findViewById(R.id.cardContainer);
        RoutineCardAdapter adapter = new RoutineCardAdapter(routines);
        cardContainer.setLayoutManager(new GridLayoutManager(getContext(),getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 2));
        cardContainer.setAdapter(adapter);
        return root;
    }



}