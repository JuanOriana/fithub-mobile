package com.example.fithub_mobile.backend;

import androidx.lifecycle.LiveData;


import com.example.fithub_mobile.backend.models.FullCycle;
import com.example.fithub_mobile.backend.models.FullCycleExercise;
import com.example.fithub_mobile.backend.models.FullExercise;
import com.example.fithub_mobile.backend.models.PagedList;
import com.example.fithub_mobile.backend.models.Sport;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiCycleService {

    @GET("routines/{routineId}/cycles")
    LiveData<ApiResponse<PagedList<FullCycle>>> getCycles(@Path("routineId") int routineId);

    @GET("cycles/{cycleId}/exercises")
    LiveData<ApiResponse<PagedList<FullCycleExercise>>> getCycleExercises(@Path("cycleId") int cycleId);


}
