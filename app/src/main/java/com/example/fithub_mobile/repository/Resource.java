package com.example.fithub_mobile.repository;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import static com.example.fithub_mobile.repository.Status.ERROR;
import static com.example.fithub_mobile.repository.Status.LOADING;
import static com.example.fithub_mobile.repository.Status.SUCCESS;

import com.example.fithub_mobile.backend.models.Error;

public class Resource<T> {

    @NonNull
    private final Status status;

    @Nullable
    private final Error error;

    @Nullable
    private final T data;

    @NotNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public Error getError() {
        return error;
    }

    @Nullable
    public T getData() {
        return data;
    }

    public Resource(@NonNull Status status, @Nullable T data, @Nullable Error error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(Error error, @Nullable T data) {
        return new Resource<>(ERROR, data, error);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }
}
