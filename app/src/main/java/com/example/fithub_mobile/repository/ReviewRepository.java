package com.example.fithub_mobile.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.fithub_mobile.App;
import com.example.fithub_mobile.backend.ApiClient;
import com.example.fithub_mobile.backend.ApiResponse;
import com.example.fithub_mobile.backend.ApiReviewService;
import com.example.fithub_mobile.backend.ApiRoutineService;
import com.example.fithub_mobile.backend.models.Credentials;
import com.example.fithub_mobile.backend.models.Review;
import com.example.fithub_mobile.backend.models.Token;

public class ReviewRepository {

    private final ApiReviewService apiService;

    public ReviewRepository(App application) {
        this.apiService = ApiClient.create(application, ApiReviewService.class);
    }

    public LiveData<Resource<Void>> addReview(int routineId, Review review) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.addReview(routineId, review);
            }
        }.asLiveData();
    }

}
