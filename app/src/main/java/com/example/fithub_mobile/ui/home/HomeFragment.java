package com.example.fithub_mobile.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithub_mobile.R;
import com.example.fithub_mobile.RoutineCard;
import com.example.fithub_mobile.RoutineCardAdapter;
import com.example.fithub_mobile.RoutineCardData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView cardContainer;
    private ArrayList<RoutineCardData> routines = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        routines.add(new RoutineCardData(1,"Titulo","Prueba",4));
        routines.add(new RoutineCardData(2,"Titulo","Prueba",5));
        routines.add(new RoutineCardData(3,"Titulo","Prueba",1));

        cardContainer = root.findViewById(R.id.cardContainer);
        RoutineCardAdapter adapter = new RoutineCardAdapter(routines);
        cardContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        cardContainer.setAdapter(adapter);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(
            @NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("SEARCHING", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }


}