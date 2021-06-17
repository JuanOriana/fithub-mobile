package com.example.fithub_mobile.ui.search;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.fithub_mobile.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterDialogFragment extends DialogFragment
{
    int[] filterState = new int[4];
    static final int SORT_CRITERIA = 0;
    static final int ORDER_CRITERIA = 1;
    static final int FILTER_CRITERIA = 2;
    static final int BASED_CRITERIA = 3;

    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_filter, null);
        builder.setView(view).setMessage(R.string.filter_dialog_title);

        Spinner sortCriteriaSpinner = view.findViewById(R.id.sort_spinner);

        ArrayAdapter<CharSequence> custAdapter = new CustomFilterSpinnerAdapter(view.getContext(),
                Arrays.asList(getResources().getStringArray(R.array.sort_items)),getString(R.string.sort_spinner_placeholder));
        sortCriteriaSpinner.setAdapter(custAdapter);
        sortCriteriaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterState[SORT_CRITERIA] = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner sortOrderSpinner = view.findViewById(R.id.order_spinner);
       CustomFilterSpinnerAdapter orderAdapter = new CustomFilterSpinnerAdapter(view.getContext(),
               Arrays.asList(getResources().getStringArray(R.array.sort_order_items)),getString(R.string.order_spinner_placeholder));
        // Specify the layout to use when the list of choices appears
        orderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sortOrderSpinner.setAdapter(orderAdapter);
        sortOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterState[ORDER_CRITERIA] = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner basedFilterSpinner = view.findViewById(R.id.based_on_spinner);
        CustomFilterSpinnerAdapter basedAdapter = new CustomFilterSpinnerAdapter(view.getContext(),
                new ArrayList<>(),getString(R.string.filter_spinner_default_item));
        basedFilterSpinner.setAdapter(basedAdapter);
        basedFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterState[BASED_CRITERIA] = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner filterCriteriaSpinner = view.findViewById(R.id.filter_spinner);
        CustomFilterSpinnerAdapter filterAdapter = new CustomFilterSpinnerAdapter(view.getContext(),
                Arrays.asList(getResources().getStringArray(R.array.filter_items)),getString(R.string.filter_spinner_default_item));
        filterCriteriaSpinner.setAdapter(filterAdapter);
        filterCriteriaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterState[FILTER_CRITERIA] = position;
                if(position == 1){
                    basedAdapter.clear();
                    basedAdapter.addAll(getResources().getStringArray(R.array.rating_items));
                    basedAdapter.setPlaceholderMessage(getString(R.string.rating_spinner_placeholder));
                }
                else if(position == 2) {
                    basedAdapter.clear();
                    basedAdapter.addAll(getResources().getStringArray(R.array.difficulty_items));
                    basedAdapter.setPlaceholderMessage(getString(R.string.difficulty_spinner_placeholder));
                }
                else {
                    basedAdapter.clear();
                    basedAdapter.setPlaceholderMessage(getString(R.string.empty_based_spinner_placeholder));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final AlertDialog filterDialog = builder.create();
        Button positiveButton  = view.findViewById(R.id.button5);
        positiveButton.setOnClickListener(v -> {
            SearchFragment mySF = (SearchFragment) getTargetFragment();
            if(mySF != null)
                mySF.onDialogPositiveClick(filterState);
            filterDialog.dismiss();
        });
        Button negativeButton  = view.findViewById(R.id.button4);
        negativeButton.setOnClickListener(v -> {
            filterDialog.dismiss();
        });
        return filterDialog;
    }
}

class CustomFilterSpinnerAdapter extends ArrayAdapter<CharSequence> {

    CharSequence placeholderMessage;

    public CustomFilterSpinnerAdapter(Context context, List<CharSequence> items, CharSequence placeholderMessage) {
        super(context, android.R.layout.simple_spinner_item, items);
        this.placeholderMessage = placeholderMessage;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        if (position == 0) {
            return initialSelection(true);
        }
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (position == 0) {
            return initialSelection(false);
        }
        return getCustomView(position, convertView, parent);
    }


    @Override
    public int getCount() {
        return super.getCount() + 1; // Adjust for initial selection item
    }

    public void setPlaceholderMessage(CharSequence newPlaceholder){
        placeholderMessage = newPlaceholder;
    }

    private View initialSelection(boolean dropdown) {
        // Just an example using a simple TextView. Create whatever default view
        // to suit your needs, inflating a separate layout if it's cleaner.
        TextView view = new TextView(getContext());
        view.setTextSize(16);
        view.setText(placeholderMessage);
        int spacing = getContext().getResources().getDimensionPixelSize(R.dimen.text_margin);
        view.setPadding(17, spacing, 0, spacing);

        if (dropdown) { // Hidden when the dropdown is opened
            view.setHeight(0);
        }

        return view;
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        // Distinguish "real" spinner items (that can be reused) from initial selection item
        View row = convertView != null && !(convertView instanceof TextView)
                ? convertView :
                LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);

        position = position - 1; // Adjust for initial selection item
        CharSequence item = getItem(position);

        // ... Resolve views & populate with data ...
        TextView tRow = (TextView) row;
        tRow.setText(item);
        return tRow;
    }
}


