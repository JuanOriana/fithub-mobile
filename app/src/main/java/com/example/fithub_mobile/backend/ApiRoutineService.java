package com.example.fithub_mobile.backend;

import androidx.lifecycle.LiveData;


import com.example.fithub_mobile.backend.models.FullRoutine;
import com.example.fithub_mobile.backend.models.PagedList;
import com.example.fithub_mobile.backend.models.Sport;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRoutineService {

    @GET("routines?size=50")
    LiveData<ApiResponse<PagedList<FullRoutine>>> getRoutines();

    @GET("routines?size=50")
    LiveData<ApiResponse<PagedList<FullRoutine>>> getRoutinesByDiff(@Query("difficulty") String difficulty);

    @GET("routines/{routineId}")
    LiveData<ApiResponse<FullRoutine>> getRoutine(@Path("routineId") int routineId);


}
