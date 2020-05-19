package com.example.myfirstfinalapplication;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.myfirstfinalapplication.MyNotificationChannel.CHANNEL_ID;

public class AlertReceiver extends BroadcastReceiver {
    private NotificationManagerCompat manager;

    @Override
    public void onReceive(Context context, Intent intent) {
        String alarmName = intent.getStringExtra("Alarm name");
        String time = intent.getStringExtra("Time");

        Vibrator v=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(3000);

        manager = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                .setContentTitle(alarmName)
                .setContentText(time)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setAutoCancel(true)
                .build();

        manager.notify(0, notification);
    }
}