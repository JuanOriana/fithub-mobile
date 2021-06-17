package com.example.fithub_mobile.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.fithub_mobile.App;
import com.example.fithub_mobile.R;
import com.example.fithub_mobile.backend.models.EmailConfirmation;
import com.example.fithub_mobile.repository.Resource;
import com.example.fithub_mobile.repository.Status;
import com.example.fithub_mobile.ui.login.Login;

public class RegisterVerification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_verification);

        Intent intent = getIntent();
        String email = intent.getData().getQueryParameter("email");
        String code = intent.getData().getQueryParameter("code");

        App app = (App)getApplication();
        app.getUserRepository().verifyEmail(new EmailConfirmation(email,code)).observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                Toast.makeText(getApplicationContext(),getText(R.string.verif_success),Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, Login.class);
                startActivity(i);
                finish();
            } else {
                Resource.defaultResourceHandler(r);
                if (r.getStatus() == Status.ERROR) {
                    Toast.makeText(getApplicationContext(), getText(R.string.verif_fail), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}