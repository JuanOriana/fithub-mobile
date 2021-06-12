package com.example.fithub_mobile.excercise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LastlyExecutedCardDataManager {
    @SerializedName("data")
    @Expose
    private final ArrayList<LastlyExecutedCardData> data = new ArrayList<>();
    private static int MAX_SIZE = 5;

    public void add(LastlyExecutedCardData newItem){
        data.remove(newItem);
        data.add(0,newItem);
        if (data.size() > MAX_SIZE)
            data.remove(data.size()-1);
    }

    public ArrayList<LastlyExecutedCardData> getData() {
        return data;
    }
}
