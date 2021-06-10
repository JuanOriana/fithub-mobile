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
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class NotificationActivity extends AppCompatActivity {

    static final private String CHANNEL_ID = "Routine Notif";
    static final private String DAY_EXTRA = "com.example.fithub_mobile.DAY";
    public static final int REQUEST_CODE_NOTIFY = 1;
    private CheckBox[] days;



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
        TimePicker tp = findViewById(R.id.notif_tp);
        days = new CheckBox[7];
        days[0] = findViewById(R.id.checkBox_mon);
        days[1] = findViewById(R.id.checkBox_tue);
        days[2] = findViewById(R.id.checkBox_wed);
        days[3] = findViewById(R.id.checkBox_thu);
        days[4] = findViewById(R.id.checkBox_fri);
        days[5] = findViewById(R.id.checkBox_sat);
        days[6] = findViewById(R.id.checkBox_sun);

        acceptBtn.setOnClickListener(v->{
            Calendar calendar = Calendar.getInstance();
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            for (int i = 0; i < 7; i ++){
                if (days[i].isChecked()){
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.DAY_OF_WEEK,i+1);
                    calendar.set(Calendar.HOUR_OF_DAY, tp.getHour());
                    calendar.set(Calendar.MINUTE, tp.getMinute());
                    Intent pending = new Intent( this, NotifyHandlerReceiver.class );
                    pending.putExtra(DAY_EXTRA,42+i);
                    PendingIntent pendingNotifyIntent = PendingIntent.getBroadcast(
                            this,
                            42+i,
                            pending,
                            0);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY * 7, pendingNotifyIntent);
                }
            }

            finish();
            Toast.makeText(getApplicationContext(),"Reminder created succesfully",Toast.LENGTH_SHORT).show();

        });

        cancelBtn.setOnClickListener(v->{
            finish();
        });
    }

}