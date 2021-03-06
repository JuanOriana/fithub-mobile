package com.example.fithub_mobile.backend;

import androidx.lifecycle.LiveData;


import com.example.fithub_mobile.backend.models.PagedList;
import com.example.fithub_mobile.backend.models.Sport;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiSportService {

    @GET("sports")
    LiveData<ApiResponse<PagedList<Sport>>> getSports();

    @POST("sports")
    LiveData<ApiResponse<Sport>> addSport(@Body Sport sport);

    @GET("sports/{sportId}")
    LiveData<ApiResponse<Sport>> getSport(@Path("sportId") int sportId);

    @PUT("sports/{sportId}")
    LiveData<ApiResponse<Sport>> modifySport(@Path("sportId") int sportId, @Body Sport sport);

    @DELETE("sports/{sportId}")
    LiveData<ApiResponse<Void>> deleteSport(@Path("sportId") int sportId);
}
