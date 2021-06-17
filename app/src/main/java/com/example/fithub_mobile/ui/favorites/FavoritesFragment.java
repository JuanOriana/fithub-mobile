package com.example.fithub_mobile.ui.favorites;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithub_mobile.App;
import com.example.fithub_mobile.R;
import com.example.fithub_mobile.backend.models.FullRoutine;
import com.example.fithub_mobile.repository.Resource;
import com.example.fithub_mobile.repository.Status;
import com.example.fithub_mobile.ui.routine.RoutineCardAdapter;

import java.util.ArrayList;

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

        DisplayMetrics displayMetrics = root.getContext().getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        if(dpWidth >= 1024 || (dpWidth >= 600 && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)) {
            cardContainer.setLayoutManager(new GridLayoutManager(getContext(),getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 3));

        } else {
            cardContainer.setLayoutManager(new GridLayoutManager(getContext(), requireActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 2));
        }

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