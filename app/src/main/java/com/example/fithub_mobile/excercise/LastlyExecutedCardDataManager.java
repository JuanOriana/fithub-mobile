package com.example.fithub_mobile.excercise;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.fithub_mobile.ExerciseQueueRealState;
import com.example.fithub_mobile.backend.models.FullCycleExercise;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LastlyExecutedCardDataManager {

    private static LastlyExecutedCardDataManager single_instance = null;
    @SerializedName("data")
    @Expose
    private ArrayList<LastlyExecutedCardData> data;
    private static int MAX_SIZE = 5;

    public LastlyExecutedCardDataManager() {
        data = new ArrayList<>();
    }

    // static method to create instance of Singleton class
    public static LastlyExecutedCardDataManager getInstance()
    {
        if (single_instance == null)
            single_instance = new LastlyExecutedCardDataManager();

        return single_instance;
    }

    public void add(Context context, LastlyExecutedCardData newItem){
        data.remove(newItem);
        data.add(0,newItem);

        if (data.size() > MAX_SIZE)
            data.remove(data.size()-1);

        Gson gson = new Gson();
        SharedPreferences sp = context.getSharedPreferences("lastly_exec",0);
        String stringedData = gson.toJson(data);
        sp.edit().putString("lastly_exec_ex",stringedData).apply();
    }

    public ArrayList<LastlyExecutedCardData> getData(Context context) {

        // Habia data cargada?
        Gson gson = new Gson();
        SharedPreferences sp = context.getSharedPreferences("lastly_exec",0);
        String stringedData = sp.getString("lastly_exec_ex","");
        Type type = new TypeToken<ArrayList<LastlyExecutedCardData>>() {}.getType();
        data = gson.fromJson(stringedData,type);
        if (data == null){
            data = new ArrayList<>();
        }

        return data;
    }



}
