package com.delta_inductions.eventapp;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.IOException;

import static android.content.Context.ALARM_SERVICE;
import static com.delta_inductions.eventapp.BaseApp.CHANNEL_ID2;
public class Receiver2 extends BroadcastReceiver  {
    private AlarmManager alarmManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        Uri notification = Uri.parse("android.resource://com.delta_inductions.eventapp/raw/notification_sound");
        alarmManager =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if(!intent.hasExtra("dismiss")) {
            Intent Activityintent = new Intent(context, MainActivity.class);
            PendingIntent contentintent = PendingIntent.getActivity(context, 3, Activityintent, 0);
            Notification notification2 = new NotificationCompat.Builder(context, CHANNEL_ID2)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(intent.getStringExtra("title"))
                    .setContentText(intent.getStringExtra("message"))
                    .setCategory(ALARM_SERVICE)
                    .setSound(notification)
                    .setContentIntent(contentintent)
                    .build();
            notificationManager.notify(2, notification2);
        }
       else  {
           notificationManager.cancelAll();
           deleteReminder(1,context);
        }

    }
    public void deleteReminder(int id,Context context) {
        Intent i = new Intent(context, Receiver2.class);
        PendingIntent pi = PendingIntent.getBroadcast(context,
                id, i, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pi);
    }
}
