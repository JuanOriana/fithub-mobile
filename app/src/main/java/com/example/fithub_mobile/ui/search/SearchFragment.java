package com.example.fithub_mobile.ui.search;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fithub_mobile.R;
import com.example.fithub_mobile.RoutineCard;
import com.example.fithub_mobile.ui.favorites.FavoritesViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

        private SearchViewModel searchViewModel;
        private LinearLayout cardContainer;
        private final ArrayList<RoutineCard> routines = new ArrayList<>();

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            searchViewModel =
                    new ViewModelProvider(this).get(SearchViewModel.class);
            View root = inflater.inflate(R.layout.fragment_favorites, container, false);

            cardContainer = root.findViewById(R.id.cardContainer);
            addRoutine(new RoutineCard(getActivity(), "Uno", "Una descripcion random", 4));
            addRoutine(new RoutineCard(getActivity(), "Dos", "Una descripcion random", 2));
            addRoutine(new RoutineCard(getActivity(), "Tres", "Una descripcion random", 5));

            setHasOptionsMenu(true);

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
                cardContainer.removeAllViews();
                RoutineCard chosen = getRoutineByTitle(query);
                if(chosen != null)
                    cardContainer.addView(chosen);
                hideSoftKeyboard(requireActivity());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }
    public void addRoutine(RoutineCard routine){
        cardContainer.addView(routine);
        routines.add(routine);
    }

    private RoutineCard getRoutineByTitle(String title){
        for (RoutineCard r : routines){
            if(r.getTitle().equals(title))
                return r;
        }
        return null;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }
}
