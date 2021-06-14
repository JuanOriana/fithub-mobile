package com.example.fithub_mobile;

import android.app.Application;

import com.example.fithub_mobile.repository.CycleRepository;
import com.example.fithub_mobile.repository.ExerciseImageRepository;
import com.example.fithub_mobile.repository.FavouriteRepository;
import com.example.fithub_mobile.repository.RoutineRepository;
import com.example.fithub_mobile.repository.SportRepository;
import com.example.fithub_mobile.repository.UserRepository;


public class App extends Application {

    private AppPreferences preferences;
    private UserRepository userRepository;
    private SportRepository sportRepository;
    private RoutineRepository routineRepository;
    private FavouriteRepository favouriteRepository;
    private CycleRepository cycleRepository;
    private ExerciseImageRepository exerciseImageRepository;

    public AppPreferences getPreferences() { return preferences; }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public SportRepository getSportRepository() {
        return sportRepository;
    }

    public FavouriteRepository getFavouriteRepository() {
        return favouriteRepository;
    }

    public RoutineRepository getRoutineRepository() {
        return routineRepository;
    }

    public CycleRepository getCycleRepository() {
        return cycleRepository;
    }

    public ExerciseImageRepository getExerciseImageRepository() {
        return exerciseImageRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = new AppPreferences(this);

        userRepository = new UserRepository(this);

        sportRepository = new SportRepository(this);

        routineRepository = new RoutineRepository(this);

        favouriteRepository = new FavouriteRepository(this);

        cycleRepository = new CycleRepository(this);

        exerciseImageRepository = new ExerciseImageRepository(this);
    }
}
