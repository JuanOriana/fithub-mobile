package com.example.fithub_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.widget.Button;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        Button cancelBtn = findViewById(R.id.cancel_btn_notif);
        Button acceptBtn = findViewById(R.id.accept_btn);

        cancelBtn.setOnClickListener(v->{
            finish();
        });
    }
}