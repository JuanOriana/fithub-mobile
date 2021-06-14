package com.example.fithub_mobile.backend;

import androidx.lifecycle.LiveData;

import com.example.fithub_mobile.backend.models.FullImage;
import com.example.fithub_mobile.backend.models.PagedList;
import com.example.fithub_mobile.backend.models.Sport;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiExerciseImageService {
    @GET("exercises/{exerciseId}/images")
    LiveData<ApiResponse<PagedList<FullImage>>> getExerciseImages(@Path("exerciseId") int exerciseId);
}
