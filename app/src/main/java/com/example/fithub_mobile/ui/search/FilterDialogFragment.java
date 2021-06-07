package com.example.fithub_mobile.ui.search;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.fithub_mobile.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FilterDialogFragment extends DialogFragment
{
    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_filter, null);
        builder.setMessage(R.string.filter_dialog_title)
                .setPositiveButton(R.string.save_btn, (dialog, id) -> {

                })
                .setNegativeButton(R.string.cancel_btn, (dialog, id) -> {
                })
        .setView(view);

        Spinner spinner = view.findViewById(R.id.filter_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.filter_items, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        return builder.create();
    }
}
