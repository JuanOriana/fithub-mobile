package com.example.fithub_mobile.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fithub_mobile.Login;
import com.example.fithub_mobile.R;
import com.example.fithub_mobile.RoutineCard;
import com.example.fithub_mobile.RoutineCardAdapter;
import com.example.fithub_mobile.RoutineCardData;

import java.util.ArrayList;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private RecyclerView cardContainer;
    private ArrayList<RoutineCardData> routines = new ArrayList<>();
    SharedPreferences sp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        sp = getContext().getSharedPreferences("login",getContext().MODE_PRIVATE);

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        String fn = sp.getString("firstname", "John");
        String ln = sp.getString("lastname", "Doe");
        String name = fn + " " + ln;

        TextView username = root.findViewById(R.id.userName);
        username.setText(name);

        cardContainer = root.findViewById(R.id.cardContainer);

        routines.add(new RoutineCardData(1,"Titulo","Prueba",4));
        routines.add(new RoutineCardData(2,"Titulo","Prueba",5));
        routines.add(new RoutineCardData(3,"Titulo","Prueba",1));

        cardContainer = root.findViewById(R.id.cardContainer);
        RoutineCardAdapter adapter = new RoutineCardAdapter(routines);
        cardContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        cardContainer.setAdapter(adapter);

        return root;
    }

    public void goToLogin() {
        Intent i = new Intent(getContext(), Login.class);
        startActivity(i);
        getActivity().finish();
    }

    public void logOut(View view) {
        sp.edit().putBoolean("logged",false).apply();
        goToLogin();
    }

    public void editProfile(View view) {
        Toast.makeText(getContext(), "A editar perfil", Toast.LENGTH_LONG).show();
        goToEdit();
    }

    private void goToEdit() {
//        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.navigation_profile, EditProfileFragment.class, null);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }

}