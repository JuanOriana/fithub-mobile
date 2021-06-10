package com.example.fithub_mobile.ui.search;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;

import com.example.fithub_mobile.R;

import org.jetbrains.annotations.NotNull;

public class FilterDialogFragment extends DialogFragment
{
    Integer selectedSortingCriteria;
    Integer selectedOrderCriteria;
    Integer selectedFilterCriteria;
    Integer selectedBasedCriteria;
    Boolean firstTimeSelectingCriteria = true;
    Boolean firstTimeSelectingOrder = true;
    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_filter, null);
        builder.setMessage(R.string.filter_dialog_title)
                .setPositiveButton(R.string.save_btn, (dialog, id) -> {
                    SearchFragment mySF = (SearchFragment) getTargetFragment();
                    if(mySF != null)
                    mySF.onDialogPositiveClick(this);
                })
                .setNegativeButton("cancelar", (dialog, id) -> {
                })
        .setView(view);

        Spinner sortCriteriaSpinner = view.findViewById(R.id.sort_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.sort_items, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sortCriteriaSpinner.setAdapter(adapter);
        sortCriteriaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSortingCriteria = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner sortOrderSpinner = view.findViewById(R.id.order_spinner);
        ArrayAdapter<CharSequence> orderAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.sort_order_items, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        orderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sortOrderSpinner.setAdapter(orderAdapter);
        sortOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOrderCriteria = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner basedFilterSpinner = view.findViewById(R.id.based_on_spinner);
        ArrayAdapter<CharSequence> basedAdapter = new ArrayAdapter<>(view.getContext(),android.R.layout.simple_spinner_item);
        basedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        basedFilterSpinner.setAdapter(basedAdapter);
        basedFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBasedCriteria = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CharSequence[] ratingList = {"Choose rating","1","2","3","4","5"};
        CharSequence[] difficultyList = {"Choose difficulty","Easy","Medium","Hard"};

        Spinner filterCriteriaSpinner = view.findViewById(R.id.filter_spinner);
        ArrayAdapter<CharSequence> filterAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.filter_items, android.R.layout.simple_spinner_item);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterCriteriaSpinner.setAdapter(filterAdapter);
        filterCriteriaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFilterCriteria = position;
                if(position == 1){
                    basedAdapter.clear();
                    basedAdapter.addAll(ratingList);
                }
                else if(position == 2) {
                    basedAdapter.clear();
                    basedAdapter.addAll(difficultyList);
                }
                else basedAdapter.clear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return builder.create();
    }
}


