package com.example.fithub_mobile.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithub_mobile.R;
import com.example.fithub_mobile.excercise.LastlyExecutedCard;
import com.example.fithub_mobile.excercise.LastlyExecutedCardData;
import com.example.fithub_mobile.excercise.LastlyExecutedCardDataManager;
import com.example.fithub_mobile.routine.RoutineCardAdapter;
import com.example.fithub_mobile.routine.RoutineCardData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView cardContainer;
    private LinearLayout recentContainer;
    private ArrayList<RoutineCardData> routines = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recentContainer = root.findViewById(R.id.recent_container);

        SharedPreferences sp = requireActivity().getSharedPreferences("lastly_exec", Context.MODE_PRIVATE);
        String stringedData = sp.getString("lastly_exec_ex","");
        Gson gson = new Gson();
        Type type = new TypeToken<LastlyExecutedCardDataManager>() {}.getType();
        LastlyExecutedCardDataManager lastlyExecManager = gson.fromJson(stringedData,type);
        if (lastlyExecManager == null){
            TextView noDataText = new TextView(root.getContext());
            noDataText.setText("NO HAY EJERCICIOS");
            recentContainer.addView(noDataText);
        }
        else {
            for (LastlyExecutedCardData item : lastlyExecManager.getData())
            recentContainer.addView(new LastlyExecutedCard(root.getContext(), item.getTitle(), item.getDescription()));
        }



        routines.add(new RoutineCardData(1,"Titulo","Prueba",4,
                "pollo","https://ep00.epimg.net/elcomidista/imagenes/2020/09/02/articulo/1599041159_343586_1599041590_rrss_normal.jpg", RoutineCardData.HARD_DIFFICULTY));
        routines.add(new RoutineCardData(2,"Titulo","Prueba",5,
                "pollo","https://ep00.epimg.net/elcomidista/imagenes/2020/09/02/articulo/1599041159_343586_1599041590_rrss_normal.jpg", RoutineCardData.HARD_DIFFICULTY));
        routines.add(new RoutineCardData(3,"Titulo","Prueba",1,
                "pollo","https://ep00.epimg.net/elcomidista/imagenes/2020/09/02/articulo/1599041159_343586_1599041590_rrss_normal.jpg", RoutineCardData.HARD_DIFFICULTY));

        cardContainer = root.findViewById(R.id.cardContainer);
        RoutineCardAdapter adapter = new RoutineCardAdapter(routines);
        cardContainer.setLayoutManager(new GridLayoutManager(getContext(),getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 2));
        cardContainer.setAdapter(adapter);
        return root;
    }



}