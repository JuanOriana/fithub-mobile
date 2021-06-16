package com.example.fithub_mobile.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.fithub_mobile.backend.models.FullRoutine;

import java.util.List;

public class SearchViewModel extends ViewModel {
    private String searchQuery = "";
    int[] filters  = new int[4];
    int orientation;

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public void setFilters(int[] filters){
        this.filters = filters.clone();
    }

    public void clearFilters(){
        for(int i = 0 ; i  < filters.length ; i ++){
            this.filters[i] = 0;
        }
    }

    public int getBasedCriteria(){
        return filters[FilterDialogFragment.BASED_CRITERIA];
    }
    public int getFilterCriteria(){
        return filters[FilterDialogFragment.FILTER_CRITERIA];
    }
    public int getOrderCriteria(){
        return filters[FilterDialogFragment.ORDER_CRITERIA];
    }
    public int getSortingCriteria(){
        return filters[FilterDialogFragment.SORT_CRITERIA];
    }

    public void setBasedCriteria(int basedCriteria){
        filters[FilterDialogFragment.BASED_CRITERIA] = basedCriteria;
    }
    public void setFilterCriteria(int filterCriteria){
        filters[FilterDialogFragment.FILTER_CRITERIA] = filterCriteria;
    }
    public void setOrderCriteria(int orderCriteria){
        filters[FilterDialogFragment.ORDER_CRITERIA] = orderCriteria;
    }
    public void setSortingCriteria(int sortingCriteria){
        filters[FilterDialogFragment.SORT_CRITERIA] = sortingCriteria;
    }
}
