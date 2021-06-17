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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fithub_mobile.backend.models.Credentials;
import com.example.fithub_mobile.backend.models.RegisterCredentials;
import com.example.fithub_mobile.repository.Resource;
import com.example.fithub_mobile.repository.Status;


public class Register extends AppCompatActivity {

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sp = getSharedPreferences("login",MODE_PRIVATE);

    }

    public void signUp(View view) {
        EditText fnView = findViewById(R.id.firstname_input);
        String fn = fnView.getText().toString();
        EditText lnView = findViewById(R.id.lastname_input);
        String ln = lnView.getText().toString();
        EditText emailView = findViewById(R.id.registerEmailInput);
        String email = emailView.getText().toString();
        EditText passView = findViewById(R.id.edit_password);
        String pass = passView.getText().toString();
        EditText passConfView = findViewById(R.id.edit_confirm_password);
        String passConf = passConfView.getText().toString();

        boolean error = false;

        if (fn.trim().length() == 0){
            error = true;
            fnView.setError(getText(R.string.invalid_fn));
        }

        if (ln.trim().length() == 0){
            error = true;
            lnView.setError(getText(R.string.invalid_ln));
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.trim().length() == 0){
            error = true;
            emailView.setError(getText(R.string.invalid_email));
        }

        if (pass.trim().length() == 0){
            error = true;
            passView.setError(getText(R.string.invalid_password));
        }

        if (passConf.trim().length() == 0 || !passConf.equals(pass)){
            error = true;
            passConfView.setError(getText(R.string.invalid_password_conf));
        }

        if (error){
            Toast toast=Toast.makeText(getApplicationContext(),getText(R.string.error_params),Toast.LENGTH_SHORT);
            toast.show();
            return;
        }


        Button regButton = findViewById(R.id.register_btn);
        regButton.setEnabled(false);
        RegisterCredentials credentials = new RegisterCredentials(email,pass,fn,ln,email,"https://png.pngitem.com/pimgs/s/421-4213053_default-avatar-icon-hd-png-download.png");
        App app = (App)getApplication();
        app.getUserRepository().register(credentials).observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                fnView.setText("");
                lnView.setText("");
                emailView.setText("");
                passView.setText("");
                passConfView.setText("");
                Toast.makeText(getApplicationContext(),getText(R.string.success_register),Toast.LENGTH_LONG).show();
                regButton.setEnabled(true);
            } else {
                Resource.defaultResourceHandler(r);
                if (r.getStatus() == Status.ERROR) {
                    Toast.makeText(getApplicationContext(), getText(R.string.existing_account), Toast.LENGTH_LONG).show();
                    regButton.setEnabled(true);
                }
            }
        });
    }

    public void goToMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void goToLogin(View view) {
        Intent i = new Intent(this, Login.class);
        startActivity(i);
        finish();
    }
}
