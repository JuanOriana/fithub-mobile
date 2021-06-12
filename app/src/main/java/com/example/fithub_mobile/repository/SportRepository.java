package com.example.fithub_mobile.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.fithub_mobile.App;
import com.example.fithub_mobile.backend.ApiClient;
import com.example.fithub_mobile.backend.ApiResponse;
import com.example.fithub_mobile.backend.ApiSportService;
import com.example.fithub_mobile.backend.models.PagedList;
import com.example.fithub_mobile.backend.models.Sport;

public class SportRepository {

    private final ApiSportService apiService;

    public SportRepository(App application) {
        this.apiService = ApiClient.create(application, ApiSportService.class);
    }

    public LiveData<Resource<PagedList<Sport>>> getSports() {
        return new NetworkBoundResource<PagedList<Sport>, PagedList<Sport>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Sport>>> createCall() {
                return apiService.getSports();
            }
        }.asLiveData();
    }

    public LiveData<Resource<Sport>> getSport(int sportId) {
        return new NetworkBoundResource<Sport, Sport>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Sport>> createCall() {
                return apiService.getSport(sportId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Sport>> addSport(Sport sport) {
        return new NetworkBoundResource<Sport, Sport>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Sport>> createCall() {
                return apiService.addSport(sport);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Sport>> modifySport(Sport sport) {
        return new NetworkBoundResource<Sport, Sport>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Sport>> createCall() {
                return apiService.modifySport(sport.getId(), sport);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> deleteSport(int sportId) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.deleteSport(sportId);
            }
        }.asLiveData();
    }
}
