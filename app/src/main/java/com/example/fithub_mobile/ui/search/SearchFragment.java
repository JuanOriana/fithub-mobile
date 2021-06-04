package com.example.fithub_mobile.ui.search;

import android.app.Activity;
import android.graphics.text.LineBreaker;
import android.os.Build;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fithub_mobile.R;
import com.example.fithub_mobile.RoutineCard;
import com.example.fithub_mobile.RoutineCardData;
import com.example.fithub_mobile.ui.favorites.FavoritesViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

        private SearchViewModel searchViewModel;
        private TextView routineNotFound;
        private LinearLayout cardContainer;
        private final ArrayList<RoutineCard> routines = new ArrayList<>();
        private ArrayList<RoutineCardData> extractedRoutines = new ArrayList<>();

        @RequiresApi(api = Build.VERSION_CODES.Q)
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            searchViewModel =
                    new ViewModelProvider(this).get(SearchViewModel.class);
            View root = inflater.inflate(R.layout.fragment_favorites, container, false);

            cardContainer = root.findViewById(R.id.cardContainer);

            extractedRoutines.add(new RoutineCardData(1,"Titulo","Prueba",4));
            extractedRoutines.add(new RoutineCardData(2,"Titulo","Prueba",5));
            extractedRoutines.add(new RoutineCardData(3,"Titulo","Prueba",1));

            cardContainer.addView(new RoutineCard(getActivity(),extractedRoutines.get(0)));
            cardContainer.addView(new RoutineCard(getActivity(),extractedRoutines.get(1)));
            cardContainer.addView(new RoutineCard(getActivity(),extractedRoutines.get(2)));

            setHasOptionsMenu(true);

            routineNotFound = new TextView(this.getContext());
            routineNotFound.setTextSize(20);
            routineNotFound.setGravity(1);

            return root;
        }


    @Override
    public void onCreateOptionsMenu(
            @NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setIconifiedByDefault(false);

        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {

            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                restoreViews();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("SEARCHING", query);
                hideSoftKeyboard(requireActivity());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<RoutineCard> chosenRoutines;
                if(newText.length() >= 3){
                    cardContainer.removeAllViews();
                    chosenRoutines = getRoutinesByTitle(newText);
                    if(!chosenRoutines.isEmpty())
                        for(RoutineCard r : chosenRoutines) {
                            cardContainer.addView(r);
                        }
                    if(cardContainer.getChildCount() == 0){
                        routineNotFound.setText(R.string.NotFoundMessage);
                        cardContainer.addView(routineNotFound);
                    }
                }

                else
                    restoreViews();
                return true;
            }
        });


    }
    public void addRoutine(RoutineCard routine){
        cardContainer.addView(routine);
        routines.add(routine);
    }

    private List<RoutineCard> getRoutinesByTitle(String title){
    ArrayList<RoutineCard> chosenRoutines = new ArrayList<>();
        for (RoutineCard r : routines){
            if(r.getTitle().startsWith(title))
                chosenRoutines.add(r);
            }
        return chosenRoutines;
    }

    private void restoreViews(){
        cardContainer.removeAllViews();
        for(RoutineCard r : routines)
            cardContainer.addView(r);
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
