package com.example.fithub_mobile.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<String> query;


    public SearchViewModel() {
        query = new MutableLiveData<>();
    }

    public LiveData<String> getQuery() {
        return query;
    }
    public void setQuery(MutableLiveData<String> query) {
        this.query = query;
    }

}
