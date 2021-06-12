package com.example.fithub_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fithub_mobile.backend.models.Credentials;
import com.example.fithub_mobile.backend.models.Error;
import com.example.fithub_mobile.repository.Resource;
import com.example.fithub_mobile.repository.Status;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Login extends AppCompatActivity {

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getSharedPreferences("login",MODE_PRIVATE);

        if (sp.getBoolean("logged",false)){
            goToMainActivity();
        }
    }

    public void logIn(View view){
        EditText emailView = findViewById(R.id.loginEmailInput);
        String email = emailView.getText().toString();
        EditText passView = findViewById(R.id.loginPasswordInput);
        String pass = passView.getText().toString();
        boolean error = false;
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.trim().length() == 0){
            error = true;
            emailView.setError("The email is not valid");
        }

        if (pass.trim().length() == 0){
            error = true;
            passView.setError("The password is not valid");
        }
        if (error){
            Toast toast=Toast.makeText(getApplicationContext(),"Error in parameters",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        Credentials credentials = new Credentials(email, pass);
        App app = (App)getApplication();
        app.getUserRepository().login(credentials).observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                Log.d("LOGIN", "Funciono");
                app.getPreferences().setAuthToken(r.getData().getToken());
                sp.edit().putBoolean("logged",true).apply();
                goToMainActivity();
            } else {
                defaultResourceHandler(r);
                if (r.getStatus() == Status.ERROR)
                    Toast.makeText(getApplicationContext(),"Your username/password is not valid",Toast.LENGTH_LONG).show();
            }
        });


    }

    public void goToMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void goToRegister(View view) {
        Intent i = new Intent(this, Register.class);
        startActivity(i);
        finish();
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
}