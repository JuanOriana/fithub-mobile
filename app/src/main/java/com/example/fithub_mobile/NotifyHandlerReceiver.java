package com.example.fithub_mobile;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotifyHandlerReceiver extends BroadcastReceiver {


    static final private String CHANNEL_ID = "Routine Notif";

    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Fithub")
                .setContentText(context.getString(R.string.time_to_train))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.fav_full)
                .setAutoCancel(true);
        NotificationManager managerCompat  = (NotificationManager)context.getSystemService(Context. NOTIFICATION_SERVICE ) ;
        managerCompat.notify(42,builder.build());
    }
}