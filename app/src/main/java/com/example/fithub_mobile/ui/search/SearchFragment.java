package com.example.fithub_mobile.ui.search;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
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
import java.util.function.Predicate;

public class SearchFragment extends Fragment implements FilterDialogListener {
    public static final int ORDER_ASC = 1;
    public static final int ORDER_DESC = 2;
    public static final int RATING = 1;
    public static final int DIFFICULTY = 2;
    public static final int SPORT = 3;
    public static final int CATEGORY = 4;
    public static final int CREATION_DATE = 5;


    private SearchViewModel searchViewModel;
        private RecyclerView cardContainer;
        private String query;
        SearchView searchView;
        public ArrayList<FullRoutine> extractedRoutines = new ArrayList<>();
        public ArrayList<FullRoutine> filteredRoutines = new ArrayList<>();
        RoutineCardAdapter adapter;

        @RequiresApi(api = Build.VERSION_CODES.Q)
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            super.onCreateView(inflater,container,savedInstanceState);
            searchViewModel =
                    new ViewModelProvider(this).get(SearchViewModel.class);
            View root = inflater.inflate(R.layout.fragment_search, container, false);
            setHasOptionsMenu(true);

            cardContainer = root.findViewById(R.id.cardContainer);
            cardContainer.setLayoutManager(new GridLayoutManager(getContext(),getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 2));
            adapter = new RoutineCardAdapter(extractedRoutines);
            cardContainer.setAdapter(adapter);

            initRoutines();

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
                        for (FullRoutine routine : extractedRoutines){
                            if (rfav.getData().getContent().contains(routine)) {
                                routine.setFavourite(true);
                                adapter.notifyDataSetChanged();
                            }
                        }
                        Log.d("RUTINAS", extractedRoutines.toString());

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
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);

        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                searchView.setQuery("",false);
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                adapter.getFilter().filter("");
                if (isAdded())
                    hideSoftKeyboard(requireActivity());
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
                adapter.getFilter().filter(newText);
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
                f.show(getParentFragmentManager(),"FilterFragment");
                f.setTargetFragment(this,0);
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onDialogPositiveClick(int[] dialogState) {
       int selectedBasedCriteria = dialogState[FilterDialogFragment.BASED_CRITERIA];
       int selectedFilterCriteria = dialogState[FilterDialogFragment.FILTER_CRITERIA];
       int selectedSortingCriteria = dialogState[FilterDialogFragment.SORT_CRITERIA];
       int selectedOrderCriteria = dialogState[FilterDialogFragment.ORDER_CRITERIA];
       extractedRoutines.addAll(filteredRoutines);
       filteredRoutines.clear();
       if(selectedBasedCriteria > 0) {
           switch (selectedFilterCriteria) {
               case RATING: {
                   for (FullRoutine r : extractedRoutines)
                       if (!(r.getAverageRating() == selectedBasedCriteria))
                           filteredRoutines.add(r);
                   extractedRoutines.removeIf(routineCardData -> !(routineCardData.getAverageRating() == (selectedBasedCriteria)));
                   break;
               }
               case DIFFICULTY: {
                   for (FullRoutine r : extractedRoutines)
                       if (!r.getDifficulty().equals(selectedBasedCriteria))
                           filteredRoutines.add(r);
                   extractedRoutines.removeIf(routineCardData -> !routineCardData.getDifficulty().equals(selectedBasedCriteria));
                   break;
               }
               default:
                   break;
           }
       }
       switch (selectedSortingCriteria){
           case RATING:
               if(selectedOrderCriteria == ORDER_ASC)
                    extractedRoutines.sort((o1, o2) -> o1.getAverageRating() - o2.getAverageRating());
               else if((selectedOrderCriteria == ORDER_DESC))
                   extractedRoutines.sort((o1, o2) -> o2.getAverageRating() - o1.getAverageRating());
               break;
           case DIFFICULTY:
               if(selectedOrderCriteria == ORDER_ASC)
                   extractedRoutines.sort((o1, o2) -> o1.getDifficulty().compareTo(o2.getDifficulty()));
               else if((selectedOrderCriteria == ORDER_DESC))
                   extractedRoutines.sort((o1, o2) -> o2.getDifficulty().compareTo(o1.getDifficulty()));
               break;
           case SPORT:
               if(selectedOrderCriteria == ORDER_ASC)
                   extractedRoutines.sort((o1, o2) -> o1.getDifficulty().compareTo(o2.getDifficulty()));
               else if((selectedOrderCriteria == ORDER_DESC))
                   extractedRoutines.sort((o1, o2) -> o2.getDifficulty().compareTo(o1.getDifficulty()));
           case CATEGORY:
               if(selectedOrderCriteria == ORDER_ASC)
                   extractedRoutines.sort((o1, o2) -> o1.getDifficulty().compareTo(o2.getDifficulty()));
               else if((selectedOrderCriteria == ORDER_DESC))
                   extractedRoutines.sort((o1, o2) -> o2.getDifficulty().compareTo(o1.getDifficulty()));
           case CREATION_DATE:
               if(selectedOrderCriteria == ORDER_ASC)
                   extractedRoutines.sort((o1, o2) -> o1.getDifficulty().compareTo(o2.getDifficulty()));
               else if((selectedOrderCriteria == ORDER_DESC))
                   extractedRoutines.sort((o1, o2) -> o2.getDifficulty().compareTo(o1.getDifficulty()));

           default: break;
       }
       adapter.notifyDataSetChanged();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}

interface FilterDialogListener {
     void onDialogPositiveClick(int[] dialogState);
     void onDialogNegativeClick(DialogFragment dialog);
}
