package com.example.fithub_mobile.ui.favorites;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithub_mobile.R;
import com.example.fithub_mobile.RoutineCard;
import com.example.fithub_mobile.RoutineCardAdapter;
import com.example.fithub_mobile.RoutineCardData;

import org.jetbrains.annotations.NotNull;

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