package com.example.fithub_mobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotifyHandlerReceiver extends BroadcastReceiver {

    public static final String ACTION = "me.pepyakin.defferednotify.action.NOTIFY";

    static final private String CHANNEL_ID = "Routine Notif";

    public void onReceive(Context context, Intent intent) {
        if (ACTION.equals(intent.getAction())) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle("Fithub")
                    .setContentText(context.getString(R.string.time_to_train))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setSmallIcon(R.drawable.fav_full)
                    .setAutoCancel(true);
            NotificationManagerCompat managerCompat  = NotificationManagerCompat.from(context);
            managerCompat.notify(1,builder.build());
        }
    }
}