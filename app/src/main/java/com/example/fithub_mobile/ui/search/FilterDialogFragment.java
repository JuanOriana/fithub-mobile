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
                .setNegativeButton(R.string.cancel_btn, (dialog, id) -> {
                })
        .setView(view);

        Spinner sortCriteriaSpinner = view.findViewById(R.id.filter_spinner);
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

        return builder.create();
    }
}
