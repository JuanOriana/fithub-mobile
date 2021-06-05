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
            fnView.setError("The first name is not valid");
        }

        if (ln.trim().length() == 0){
            error = true;
            lnView.setError("The last name is not valid");
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.trim().length() == 0){
            error = true;
            emailView.setError("The email is not valid");
        }

        if (pass.trim().length() == 0){
            error = true;
            passView.setError("The password is not valid");
        }

        if (passConf.trim().length() == 0 || !passConf.equals(pass)){
            error = true;
            passConfView.setError("The password doesn't match");
        }

        if (error){
            Toast toast=Toast.makeText(getApplicationContext(),"Error in parameters",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        sp.edit().putBoolean("logged",true)
                .putString("email",email)
                .putString("firstname",fn)
                .putString("lastname", ln)
                .apply();
        goToMainActivity();
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
