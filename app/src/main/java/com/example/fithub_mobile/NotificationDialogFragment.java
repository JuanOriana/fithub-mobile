package com.example.fithub_mobile;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NotificationDialogFragment extends DialogFragment {

    public static String TAG = "NotificationDialogFragment";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_noti, null);
        builder.setMessage("Select a day and time for your routine notifications")
                .setPositiveButton("Hola", (dialog, id) -> {

                })
                .setNegativeButton("Chau", (dialog, id) -> {
                })
                .setView(view);


//        Spinner spinner = view.findViewById(R.id.notif_spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
//                R.array.notif_dialog_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
        return builder.create();
    }

}
