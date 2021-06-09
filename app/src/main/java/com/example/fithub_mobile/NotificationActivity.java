package com.example.fithub_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.Navigation;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;


public class NotificationActivity extends AppCompatActivity {

    static final private String CHANNEL_ID = "Routine Notif";
    public static final int REQUEST_CODE_NOTIFY = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        };

        Button cancelBtn = findViewById(R.id.cancel_btn_notif);
        Button acceptBtn = findViewById(R.id.accept_btn);

        acceptBtn.setOnClickListener(v->{
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            long currentTimeMs = SystemClock.elapsedRealtime();

            PendingIntent pendingNotifyIntent = PendingIntent.getBroadcast(
                    this,
                    REQUEST_CODE_NOTIFY,
                    new Intent(NotifyHandlerReceiver.ACTION),
                    PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, currentTimeMs + 10000, pendingNotifyIntent);
        });

        cancelBtn.setOnClickListener(v->{
            finish();
        });
    }
}