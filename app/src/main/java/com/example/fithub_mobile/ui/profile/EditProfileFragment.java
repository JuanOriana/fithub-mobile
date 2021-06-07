package com.example.fithub_mobile.ui.profile;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fithub_mobile.R;

public class EditProfileFragment extends Fragment {

    private EditProfileViewModel editProfileViewModel;
    EditText fnView, lnView, emailView;
    SharedPreferences sp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        editProfileViewModel =  new ViewModelProvider(this).get(EditProfileViewModel.class);

        sp = getContext().getSharedPreferences("login",getContext().MODE_PRIVATE);

        View root = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        fnView = root.findViewById(R.id.editTextFirstName);
        fnView.setText(sp.getString("firstname", "First Name"));

        lnView = root.findViewById(R.id.editTextLastName);
        lnView.setText(sp.getString("lastname", "Last Name"));

        emailView = root.findViewById(R.id.editTextEmail);
        emailView.setText(sp.getString("email", "Email"));

        Button cancelBtn = (Button) root.findViewById(R.id.cancel_btn);
        Button saveBtn = (Button) root.findViewById(R.id.save_btn);

        cancelBtn.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_navigation_editprofile_to_navigation_profile);
        });

        saveBtn.setOnClickListener(v->{
            saveChanges(v);
            Navigation.findNavController(v).navigate(R.id.action_navigation_editprofile_to_navigation_profile);
        });


        return root;
    }

    public void saveChanges(View view) {

        SharedPreferences sp = getContext().getSharedPreferences("login",getContext().MODE_PRIVATE);

        String email = emailView.getText().toString();
        String fn = fnView.getText().toString();
        String ln = lnView.getText().toString();

        boolean error = false;

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.trim().length() == 0){
            error = true;
            emailView.setError("The email is not valid");
        }

        if (fn.trim().length() == 0){
            error = true;
            fnView.setError("The first name is not valid");
        }

        if (ln.trim().length() == 0){
            error = true;
            lnView.setError("The last name is not valid");
        }

        if (error){
            Toast toast=Toast.makeText(getContext(),"Error in parameters",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("firstname", fn).putString("lastname", ln).putString("email", email);
    }

}