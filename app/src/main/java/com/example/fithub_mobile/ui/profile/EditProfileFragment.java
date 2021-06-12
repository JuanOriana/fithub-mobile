package com.example.fithub_mobile.ui.profile;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fithub_mobile.App;
import com.example.fithub_mobile.R;
import com.example.fithub_mobile.backend.models.FullUser;
import com.example.fithub_mobile.repository.Resource;
import com.example.fithub_mobile.repository.Status;
import com.squareup.picasso.Picasso;

public class EditProfileFragment extends Fragment {

    private EditProfileViewModel editProfileViewModel;
    EditText fnView, lnView, emailView;
    FullUser user;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        editProfileViewModel =  new ViewModelProvider(this).get(EditProfileViewModel.class);


        root = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        getUserData();

        Button cancelBtn = root.findViewById(R.id.cancel_btn);
        Button saveBtn = root.findViewById(R.id.save_btn);

        cancelBtn.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_navigation_editprofile_to_navigation_profile);
        });

        saveBtn.setOnClickListener(v->{
            saveChanges(v);
        });


        return root;
    }

    public void saveChanges(View view) {

        String fn = fnView.getText().toString();
        String ln = lnView.getText().toString();

        boolean error = false;


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
        user.setFirstName(fn);
        user.setLastName(ln);

        App app = (App) getActivity().getApplication();
        app.getUserRepository().editCurrentUser(user).observe(getViewLifecycleOwner(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_editprofile_to_navigation_profile);
                Toast.makeText(getContext(),"Profile edited sucessfully",Toast.LENGTH_LONG).show();

            } else {
                Resource.defaultResourceHandler(r);
                Navigation.findNavController(view).navigate(R.id.action_navigation_editprofile_to_navigation_profile);
                if (r.getStatus() == Status.ERROR)
                    Toast.makeText(getContext(),"Something went wrong editing your profile",Toast.LENGTH_LONG).show();
            }
        });


    }

    public void getUserData() {
        App app = (App) getActivity().getApplication();
        app.getUserRepository().getCurrentUser().observe(getViewLifecycleOwner(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                user = r.getData();
                fnView = root.findViewById(R.id.editTextFirstName);
                fnView.setText(user.getFirstName());

                lnView = root.findViewById(R.id.editTextLastName);
                lnView.setText(user.getLastName());

                ImageView userImg = root.findViewById(R.id.userImg);
                Picasso.get().load(user.getAvatarUrl()).into(userImg);

            } else {
                Resource.defaultResourceHandler(r);
            }
        });
    }

}