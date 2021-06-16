package com.example.fithub_mobile.ui.favorites;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithub_mobile.App;
import com.example.fithub_mobile.R;
import com.example.fithub_mobile.backend.models.FullRoutine;
import com.example.fithub_mobile.backend.models.FullUser;
import com.example.fithub_mobile.backend.models.PublicUser;
import com.example.fithub_mobile.repository.Resource;
import com.example.fithub_mobile.repository.Status;
import com.example.fithub_mobile.routine.RoutineCardAdapter;
import com.example.fithub_mobile.routine.RoutineCardData;

import java.util.ArrayList;
import java.util.Objects;

public class FavoritesFragment extends Fragment {

    private ArrayList<FullRoutine> routines = new ArrayList<>();
    private FavoritesViewModel favoritesViewModel;
    private RecyclerView cardContainer;
    TextView noFavsMessage;
    RoutineCardAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritesViewModel =
                new ViewModelProvider(this).get(FavoritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        noFavsMessage = root.findViewById(R.id.no_fav_routines_msg);
        noFavsMessage.setVisibility(View.GONE);
        cardContainer = root.findViewById(R.id.cardContainer);

        cardContainer = root.findViewById(R.id.cardContainer);
        adapter = new RoutineCardAdapter(routines);
        cardContainer.setLayoutManager(new GridLayoutManager(getContext(), requireActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 2));
        cardContainer.setAdapter(adapter);

        initRoutines();

        return root;
    }

    public void initRoutines() {
        App app = (App) requireActivity().getApplication();
        app.getFavouriteRepository().getFavourites().observe(getViewLifecycleOwner(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                assert r.getData() != null;
                routines.addAll(r.getData().getContent());
                for (FullRoutine routine : routines){
                    routine.setFavourite(true);
                }
                noFavsMessage.setVisibility(routines.size() > 0 ? View.GONE : View.VISIBLE);
                adapter.notifyDataSetChanged();

            } else {
                Resource.defaultResourceHandler(r);
            }
        });
    }
}