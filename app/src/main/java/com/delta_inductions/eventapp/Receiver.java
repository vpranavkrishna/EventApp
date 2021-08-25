package com.delta_inductions.eventapp;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import java.io.IOException;

import static android.content.Context.ALARM_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;
import static com.delta_inductions.eventapp.BaseApp.CHANNEL_ID1;
import static com.delta_inductions.eventapp.BaseApp.CHANNEL_ID2;

public class Receiver extends BroadcastReceiver {
    private static final String TAG = "Receiver";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
        Uri notification = Uri.parse("android.resource://com.delta_inductions.eventapp/raw/notification_sound");
        Intent Activityintent = new Intent(context,MainActivity.class);
        PendingIntent contentintent = PendingIntent.getActivity(context,2,Activityintent,0);
        Intent Dismissintent = new Intent(context,Receiver2.class);
        Dismissintent.putExtra("dismiss","dismiss");
        PendingIntent Dismiss = PendingIntent.getBroadcast(context,4,Dismissintent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification1 = new NotificationCompat.Builder(context,CHANNEL_ID1)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(intent.getStringExtra("title"))
                .setContentText(intent.getStringExtra("message"))
                .setContentIntent(contentintent)
                .setAutoCancel(true)
                .setColor(Color.GREEN)
                .setSound(notification)
                .setCategory(ALARM_SERVICE)
                .addAction(R.mipmap.ic_launcher,"dismiss",Dismiss)
                .build();
        notificationManager.notify(1, notification1);
    }


}
