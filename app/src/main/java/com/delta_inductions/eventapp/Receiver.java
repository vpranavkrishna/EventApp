package com.delta_inductions.eventapp;

import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class Receiver extends BroadcastReceiver {
    private static final String TAG = "Receiver";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        Log.d(TAG, "onReceive: "+intent.getStringExtra("title")+intent.getStringExtra("message"));
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification1(intent.getStringExtra("title"),intent.getStringExtra("message"));
        notificationHelper.getManager().notify(1, nb.build());

    }
}
