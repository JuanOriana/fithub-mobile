package com.example.fithub_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        sp.edit().putBoolean("logged",true)
                .putString("email",email)
                .putString("firstname","John")
                .putString("lastname", "Doe")
                .apply();
        goToMainActivity();
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
}