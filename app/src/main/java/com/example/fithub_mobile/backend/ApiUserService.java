package com.example.fithub_mobile.backend;

import androidx.lifecycle.LiveData;


import com.example.fithub_mobile.backend.models.Credentials;
import com.example.fithub_mobile.backend.models.FullUser;
import com.example.fithub_mobile.backend.models.Token;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiUserService {

    @POST("users/login")
    LiveData<ApiResponse<Token>> login(@Body Credentials credentials);

    @POST("users/logout")
    LiveData<ApiResponse<Void>> logout();

    @GET("users/current")
    LiveData<ApiResponse<FullUser>> getCurrentUser();

    @PUT("users/current")
    LiveData<ApiResponse<FullUser>> editCurrentUser(@Body FullUser user);

}
