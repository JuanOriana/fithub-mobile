package com.example.fithub_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.fithub_mobile.backend.models.Error;
import com.example.fithub_mobile.excercise.LastlyExecutedCardDataManager;
import com.example.fithub_mobile.repository.Resource;
import com.example.fithub_mobile.repository.Status;
import com.example.fithub_mobile.ui.login.Login;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("login",MODE_PRIVATE);
        if(!sp.getBoolean("logged",false)){
            goToLogin();
        }

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,R.id.navigation_search, R.id.navigation_favorites, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void goToLogin(){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
        finish();
    }

    public void logOut(View view){

        App app = (App)getApplication();
        app.getUserRepository().logout().observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                LastlyExecutedCardDataManager lastlyExecutedCardDataManager = new LastlyExecutedCardDataManager();
                lastlyExecutedCardDataManager.cleanData(view.getContext());
                sp.edit().putBoolean("logged",false).apply();
                goToLogin();
            } else {

                defaultResourceHandler(r);
            }
        });

    }

    private void defaultResourceHandler(Resource<?> resource) {
        switch (resource.getStatus()) {
            case LOADING:
                Log.d("LOGIN", "CARGANDO");
                break;
            case ERROR:
                Error error = resource.getError();
                assert error != null;
                Log.d("LOGIN", error.getDescription() + error.getCode() + "");
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


}