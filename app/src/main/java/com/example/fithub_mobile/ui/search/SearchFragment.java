package com.example.fithub_mobile.ui.search;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithub_mobile.QrScanner;
import com.example.fithub_mobile.R;
import com.example.fithub_mobile.routine.RoutineCardAdapter;
import com.example.fithub_mobile.routine.RoutineCardData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements FilterDialogListener {

    private final static int RATING  = 1;
    private final static int NAME  = 2;
    private final static int DIFFICULTY  = 3;
    private final static int ASC = 1;
    private final static int DESC = 2;

    private SearchViewModel searchViewModel;
        private TextView routineNotFound;
        private RecyclerView cardContainer;
        public ArrayList<RoutineCardData> extractedRoutines = new ArrayList<>();
        RoutineCardAdapter adapter;

        @RequiresApi(api = Build.VERSION_CODES.Q)
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            searchViewModel =
                    new ViewModelProvider(this).get(SearchViewModel.class);
            View root = inflater.inflate(R.layout.fragment_search, container, false);
            setHasOptionsMenu(true);



            extractedRoutines.add(new RoutineCardData(1,"Diácono","Prueba",4));
            extractedRoutines.add(new RoutineCardData(2,"Caballero","Prueba",2));
            extractedRoutines.add(new RoutineCardData(3,"Titán","Prueba",1));
            extractedRoutines.add(new RoutineCardData(4,"Terminator","Prueba",5));
            extractedRoutines.add(new RoutineCardData(1,"Diaccordo","Prueba",3));

            cardContainer = root.findViewById(R.id.cardContainer);
            cardContainer.setLayoutManager(new GridLayoutManager(getContext(),getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 2));
            adapter = new RoutineCardAdapter(extractedRoutines);
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
        searchView.setIconified(false);

        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                searchView.setQuery("",false);
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
//                adapter.getFilter().filter("");
//                hideSoftKeyboard(requireActivity());
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
    public void onDialogPositiveClick(DialogFragment dialog) {
       FilterDialogFragment myDialog = (FilterDialogFragment)dialog;
       switch (myDialog.selectedSortingCriteria){
           case RATING:
               if(myDialog.selectedOrderCriteria == ASC)
                    extractedRoutines.sort((o1, o2) -> o1.getRating().compareTo(o2.getRating()));
               else if((myDialog.selectedOrderCriteria == DESC))
                   extractedRoutines.sort((o1, o2) -> o2.getRating().compareTo(o1.getRating()));
               break;
           case NAME:
               if(myDialog.selectedOrderCriteria == ASC)
                   extractedRoutines.sort((o1, o2) -> o1.getTitle().compareTo(o2.getTitle()));
               else if((myDialog.selectedOrderCriteria == DESC))
                   extractedRoutines.sort((o1, o2) -> o2.getTitle().compareTo(o1.getTitle()));
               break;
           default: break;
       }
       adapter.notifyDataSetChanged();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}

interface FilterDialogListener {
     void onDialogPositiveClick(DialogFragment dialog);
     void onDialogNegativeClick(DialogFragment dialog);
}
