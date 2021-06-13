package com.example.fithub_mobile.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.fithub_mobile.App;
import com.example.fithub_mobile.backend.ApiClient;
import com.example.fithub_mobile.backend.ApiCycleService;
import com.example.fithub_mobile.backend.ApiResponse;
import com.example.fithub_mobile.backend.ApiRoutineService;
import com.example.fithub_mobile.backend.ApiSportService;
import com.example.fithub_mobile.backend.models.FullCycle;
import com.example.fithub_mobile.backend.models.FullCycleExercise;
import com.example.fithub_mobile.backend.models.FullRoutine;
import com.example.fithub_mobile.backend.models.PagedList;
import com.example.fithub_mobile.backend.models.Sport;

public class CycleRepository {

    private final ApiCycleService apiService;

    public CycleRepository(App application) {
        this.apiService = ApiClient.create(application, ApiCycleService.class);
    }


    public LiveData<Resource<PagedList<FullCycle>>> getCycles(int routineId) {
        return new NetworkBoundResource<PagedList<FullCycle>, PagedList<FullCycle>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullCycle>>> createCall() {
                return apiService.getCycles(routineId);
            }
        }.asLiveData();
    }


    public LiveData<Resource<PagedList<FullCycleExercise>>> getCycleExercises(int cycleId) {
        return new NetworkBoundResource<PagedList<FullCycleExercise>, PagedList<FullCycleExercise>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullCycleExercise>>> createCall() {
                return apiService.getCycleExercises(cycleId);
            }
        }.asLiveData();
    }

}
