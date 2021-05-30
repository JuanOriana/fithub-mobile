package com.example.fithub_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
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
        boolean error = false;
        error = error || !Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (error){
            emailView.setError("This field can not be blank");
            Toast toast=Toast.makeText(getApplicationContext(),"Error in parameters",Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();
            return;
        }
        sp.edit().putBoolean("logged",true)
                .putString("email",email)
                .putString("username","John Doe")
                .apply();
        goToMainActivity();
    }

    public void goToMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}