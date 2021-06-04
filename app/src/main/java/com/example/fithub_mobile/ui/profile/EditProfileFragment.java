package com.example.fithub_mobile.ui.profile;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

        sp = getContext().getSharedPreferences("register",getContext().MODE_PRIVATE);

        View root = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        fnView = root.findViewById(R.id.editTextTextPersonName);
        fnView.setText(sp.getString("first_name", "First Name"));

        lnView = root.findViewById(R.id.editTextTextPersonName2);
        lnView.setText(sp.getString("last_name", "Last Name"));

        emailView = root.findViewById(R.id.editTextTextPersonName3);
        emailView.setText(sp.getString("email", "Email"));

        Button cancelBtn = (Button) root.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(this::cancel);

        Button editProfile = (Button) root.findViewById(R.id.save_btn);
        editProfile.setOnClickListener(this::saveChanges);

        return root;
    }

    public void cancel(View view) {
        goToProfile();
    }

    public void saveChanges(View view) {

        SharedPreferences sp = getContext().getSharedPreferences("register",getContext().MODE_PRIVATE);

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
        editor.putString("first_name", fn).putString("last_name", ln).putString("email", email);

        goToProfile();
    }

    private void goToProfile() {
//        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.navigation_profile, ProfileFragment.class, null);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }
}