package com.example.fithub_mobile.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.fithub_mobile.App;
import com.example.fithub_mobile.backend.ApiClient;
import com.example.fithub_mobile.backend.ApiResponse;
import com.example.fithub_mobile.backend.ApiUserService;
import com.example.fithub_mobile.backend.models.Credentials;
import com.example.fithub_mobile.backend.models.FullRoutine;
import com.example.fithub_mobile.backend.models.FullUser;
import com.example.fithub_mobile.backend.models.PagedList;
import com.example.fithub_mobile.backend.models.Token;

public class UserRepository {

    private final ApiUserService apiService;

    public UserRepository(App app) {
        this.apiService = ApiClient.create(app, ApiUserService.class);
    }

    public LiveData<Resource<Token>> login(Credentials credentials) {
        return new NetworkBoundResource<Token, Token>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Token>> createCall() {
                return apiService.login(credentials);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> logout() {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.logout();
            }
        }.asLiveData();
    }

    public LiveData<Resource<FullUser>> getCurrentUser() {
        return new NetworkBoundResource<FullUser, FullUser>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<FullUser>> createCall() {
                return apiService.getCurrentUser();
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<FullRoutine>>> getUserRoutines() {
        return new NetworkBoundResource<PagedList<FullRoutine>, PagedList<FullRoutine>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<FullRoutine>>> createCall() {
                return apiService.getUserRoutines();
            }
        }.asLiveData();
    }

    public LiveData<Resource<FullUser>> editCurrentUser(FullUser user) {
        return new NetworkBoundResource<FullUser, FullUser>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<FullUser>> createCall() {
                return apiService.editCurrentUser(user);
            }
        }.asLiveData();
    }
}