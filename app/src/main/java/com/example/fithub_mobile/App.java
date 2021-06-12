package com.example.fithub_mobile;

import android.app.Application;

import com.example.fithub_mobile.repository.SportRepository;
import com.example.fithub_mobile.repository.UserRepository;


public class App extends Application {

    private AppPreferences preferences;
    private UserRepository userRepository;
    private SportRepository sportRepository;

    public AppPreferences getPreferences() { return preferences; }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public SportRepository getSportRepository() {
        return sportRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = new AppPreferences(this);

        userRepository = new UserRepository(this);

        sportRepository = new SportRepository(this);
    }
}
