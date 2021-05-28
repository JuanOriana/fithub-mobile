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

import com.example.fithub_mobile.R;
import com.example.fithub_mobile.RoutineCard;

import org.jetbrains.annotations.NotNull;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel favoritesViewModel;
    private LinearLayout cardContainer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritesViewModel =
                new ViewModelProvider(this).get(FavoritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);

        cardContainer = root.findViewById(R.id.cardContainer);

        cardContainer.addView(new RoutineCard(getActivity(),"Uno","Una descripcion random",4));
        cardContainer.addView(new RoutineCard(getActivity(),"Dos","Una descripcion random",2));
        cardContainer.addView(new RoutineCard(getActivity(),"Tres","Una descripcion random",5));
        return root;
    }
}