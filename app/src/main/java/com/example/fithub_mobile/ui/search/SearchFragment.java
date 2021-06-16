package com.example.fithub_mobile.ui.search;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Filter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithub_mobile.App;
import com.example.fithub_mobile.QrScanner;
import com.example.fithub_mobile.R;
import com.example.fithub_mobile.backend.models.FullRoutine;
import com.example.fithub_mobile.backend.models.PublicUser;
import com.example.fithub_mobile.repository.Resource;
import com.example.fithub_mobile.repository.Status;
import com.example.fithub_mobile.routine.RoutineCard;
import com.example.fithub_mobile.routine.RoutineCardAdapter;
import com.example.fithub_mobile.routine.RoutineCardData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

public class SearchFragment extends Fragment implements FilterDialogListener {

    public static final int ORDER_ASC = 1;
    public static final int ORDER_DESC = 2;
    public static final int RATING = 1;
    public static final int DIFFICULTY = 2;
    public static final int CREATION_DATE = 3;


    private SearchViewModel searchViewModel;
    private RecyclerView cardContainer;
    SearchView searchView;
    public ArrayList<FullRoutine> extractedRoutines = new ArrayList<>();
    public ArrayList<FullRoutine> filteredRoutines = new ArrayList<>();
    RoutineCardAdapter adapter;
    private boolean changedOrientation = false;


    @RequiresApi(api = Build.VERSION_CODES.Q)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        setHasOptionsMenu(true);

        initRoutines();
        cardContainer = root.findViewById(R.id.cardContainer);
        cardContainer.setLayoutManager(new GridLayoutManager(getContext(), getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 2));
        return root;
    }

    public void initRoutines() {
        App app = (App) getActivity().getApplication();
        app.getFavouriteRepository().getFavourites().observe(getViewLifecycleOwner(), rfav -> {
            if (rfav.getStatus() == Status.SUCCESS) {
                assert rfav.getData() != null;

                app.getRoutineRepository().getRoutines().observe(getViewLifecycleOwner(), r -> {
                    if (r.getStatus() == Status.SUCCESS) {
                        assert r.getData() != null;
                        extractedRoutines.addAll(r.getData().getContent());
                        for (FullRoutine routine : extractedRoutines) {
                            if (rfav.getData().getContent().contains(routine)) {
                                routine.setFavourite(true);
                            }
                        }
                        adapter = new RoutineCardAdapter(extractedRoutines);
                        cardContainer.setAdapter(adapter);
                        adapter.getFilter().filter(searchViewModel.getSearchQuery(), count -> filter());
                        adapter.notifyDataSetChanged();

                    } else {
                        Resource.defaultResourceHandler(r);
                    }
                });
            } else {
                Resource.defaultResourceHandler(rfav);
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(
            @NotNull Menu menu, @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);


        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setIconified(false);

        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                searchView.setQuery("", false);
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hideSoftKeyboard(requireActivity());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchViewModel.setSearchQuery(newText);
                if (adapter != null)
                    adapter.getFilter().filter(searchViewModel.getSearchQuery(), count -> filter());

                return true;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.camera_menu_item:
                Intent i = new Intent(getContext(), QrScanner.class);
                startActivity(i);
                return true;
            case R.id.filter_menu_item:
                FilterDialogFragment f = new FilterDialogFragment();
                f.show(getParentFragmentManager(), "FilterFragment");
                f.setTargetFragment(this, 0);
                searchViewModel.clearFilters();
                filter();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    @Override
    public void onDialogPositiveClick(int[] dialogState) {
        searchViewModel.setFilters(dialogState);
        filter();
    }

    private void filter() {
        extractedRoutines.addAll(filteredRoutines);
        filteredRoutines.clear();
        if (searchViewModel.getBasedCriteria() > 0) {
            switch (searchViewModel.getFilterCriteria()) {
                case RATING: {
                    for (FullRoutine r : extractedRoutines)
                        if (!(r.getAverageRating() == searchViewModel.getBasedCriteria()))
                            if(!filteredRoutines.contains(r))
                                filteredRoutines.add(r);
                    extractedRoutines.removeIf(routineCardData -> !(routineCardData.getAverageRating() == (searchViewModel.getBasedCriteria())));
                    break;
                }
                case DIFFICULTY: {
                    for (FullRoutine r : extractedRoutines)
                        if (!getDifficultyId(r.getDifficulty()).equals(searchViewModel.getBasedCriteria()))
                            if(!filteredRoutines.contains(r))
                                filteredRoutines.add(r);
                    extractedRoutines.removeIf(routineCardData -> !getDifficultyId(routineCardData.getDifficulty()).equals(searchViewModel.getBasedCriteria()));
                    break;
                }
                default:
                    break;
            }
        }
        switch (searchViewModel.getSortingCriteria()) {
            case RATING:
                if (searchViewModel.getOrderCriteria() == ORDER_ASC)
                    extractedRoutines.sort((o1, o2) -> o1.getAverageRating() - o2.getAverageRating());
                else if ((searchViewModel.getOrderCriteria() == ORDER_DESC))
                    extractedRoutines.sort((o1, o2) -> o2.getAverageRating() - o1.getAverageRating());
                break;
            case DIFFICULTY:
                if (searchViewModel.getOrderCriteria() == ORDER_ASC)
                    extractedRoutines.sort((o1, o2) -> getDifficultyId(o1.getDifficulty()).compareTo(getDifficultyId(o2.getDifficulty())));
                else if ((searchViewModel.getOrderCriteria() == ORDER_DESC))
                    extractedRoutines.sort((o1, o2) -> getDifficultyId(o2.getDifficulty()).compareTo(getDifficultyId(o1.getDifficulty())));
                break;
            case CREATION_DATE:
                if (searchViewModel.getOrderCriteria() == ORDER_ASC)
                    extractedRoutines.sort((o1, o2) -> Long.compare(o1.getDate(), o2.getDate()));
                else if ((searchViewModel.getOrderCriteria() == ORDER_DESC))
                    extractedRoutines.sort((o1, o2) -> Long.compare(o2.getDate(), o1.getDate()));
            default:
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDialogNegativeClick(int[] dialogState) { ;
    }

    private Integer getDifficultyId(String difficulty) {
        switch (difficulty) {
            case "rookie":
                return 1;
            case "beginner":
                return 2;
            case "intermediate":
                return 3;
            case "advanced":
                return 4;
            case "expert":
                return 5;
            default:
                return 0;
        }
    }
}

interface FilterDialogListener {
     void onDialogPositiveClick(int[] dialogState);
     void onDialogNegativeClick(int[] dialogState);
}
