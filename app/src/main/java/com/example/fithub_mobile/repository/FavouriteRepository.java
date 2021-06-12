package com.example.fithub_mobile.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.fithub_mobile.App;
import com.example.fithub_mobile.backend.ApiClient;
import com.example.fithub_mobile.backend.ApiFavouritesService;
import com.example.fithub_mobile.backend.ApiResponse;
import com.example.fithub_mobile.backend.ApiSportService;
import com.example.fithub_mobile.backend.models.FullRoutine;
import com.example.fithub_mobile.backend.models.PagedList;
import com.example.fithub_mobile.backend.models.Sport;

public class FavouriteRepository {

    private final ApiFavouritesService apiService;

    public FavouriteRepository(App application) {
        this.apiService = ApiClient.create(application, ApiFavouritesService.class);
    }

    public LiveData<Resource<PagedList<FullRoutine>>> getFavourites() {
        return new NetworkBoundResource<PagedList<FullRoutine>, PagedList<FullRoutine>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullRoutine>>> createCall() {
                return apiService.getFavourites();
            }
        }.asLiveData();
    }


    public LiveData<Resource<Void>> addFavourite(int routineId) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.addFavourite(routineId);
            }
        }.asLiveData();
    }


    public LiveData<Resource<Void>> deleteFavourite(int routineId) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.deleteFavourite(routineId);
            }
        }.asLiveData();
    }
}
