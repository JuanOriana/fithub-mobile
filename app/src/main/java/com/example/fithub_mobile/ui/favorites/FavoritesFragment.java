package com.example.fithub_mobile.ui.favorites;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithub_mobile.R;
import com.example.fithub_mobile.backend.models.FullRoutine;
import com.example.fithub_mobile.backend.models.FullUser;
import com.example.fithub_mobile.backend.models.PublicUser;
import com.example.fithub_mobile.routine.RoutineCardAdapter;
import com.example.fithub_mobile.routine.RoutineCardData;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    private ArrayList<FullRoutine> routines = new ArrayList<>();
    private FavoritesViewModel favoritesViewModel;
    private RecyclerView cardContainer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritesViewModel =
                new ViewModelProvider(this).get(FavoritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);

        cardContainer = root.findViewById(R.id.cardContainer);

        PublicUser user = new PublicUser(1,"fede","f","https://ep00.epimg.net/elcomidista/imagenes/2020/09/02/articulo/1599041159_343586_1599041590_rrss_normal.jpg",3,3);
        FullRoutine routine = new FullRoutine(1,"Hola","hola",13213123,4,true,"Advanced",user,null,null);

        routines.add(routine);
        routines.add(routine);
        routines.add(routine);
        routines.add(routine);

        cardContainer = root.findViewById(R.id.cardContainer);
        RoutineCardAdapter adapter = new RoutineCardAdapter(routines);
        cardContainer.setLayoutManager(new GridLayoutManager(getContext(),getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 2));
        cardContainer.setAdapter(adapter);

        return root;
    }
}