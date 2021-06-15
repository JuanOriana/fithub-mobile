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

public interface ApiFavouritesService {

    @GET("favourites?size=50")
    LiveData<ApiResponse<PagedList<FullRoutine>>> getFavourites();

    @POST("favourites/{routineId}/")
    LiveData<ApiResponse<Void>> addFavourite(@Path("routineId") int routineId);

    @DELETE("favourites/{routineId}/")
    LiveData<ApiResponse<Void>> deleteFavourite(@Path("routineId") int routineId);

}
