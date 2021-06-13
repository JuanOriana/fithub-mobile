package com.example.fithub_mobile.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.fithub_mobile.App;
import com.example.fithub_mobile.backend.ApiClient;
import com.example.fithub_mobile.backend.ApiExerciseImageService;
import com.example.fithub_mobile.backend.ApiFavouritesService;
import com.example.fithub_mobile.backend.ApiResponse;
import com.example.fithub_mobile.backend.models.FullImage;
import com.example.fithub_mobile.backend.models.FullRoutine;
import com.example.fithub_mobile.backend.models.PagedList;
import com.example.fithub_mobile.backend.models.Sport;

public class ExerciseImageRepository {

    private final ApiExerciseImageService apiService;

    public ExerciseImageRepository(App application) {
        this.apiService = ApiClient.create(application, ApiExerciseImageService.class);
    }

    public LiveData<Resource<PagedList<FullImage>>> getExerciseImages(int exerciseId) {
        return new NetworkBoundResource<PagedList<FullImage>, PagedList<FullImage>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullImage>>> createCall() {
                return apiService.getExerciseImages(exerciseId);
            }
        }.asLiveData();
    }

}
