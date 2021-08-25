package com.delta_inductions.eventapp;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BaseApp extends Application {
    public static final String CHANNEL_ID1 = "channel1";
    public static final String CHANNEL_ID2 ="channel2" ;
    private Uri notification = Uri.parse("android.resource://com.delta_inductions.eventapp/raw/notification_sound");
    private AudioAttributes audioAttributes = new AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel notificationChannel1 = new NotificationChannel(CHANNEL_ID1,"channel1", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel1.setDescription("This is channel1");
        notificationChannel1.enableVibration(true);
        notificationChannel1.enableLights(true);
        notificationChannel1.setSound(notification,audioAttributes);
        NotificationChannel notificationChannel2 = new NotificationChannel(CHANNEL_ID2,"channel2", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel2.setDescription("This is channel2");
        notificationChannel2.enableVibration(true);
        notificationChannel2.enableLights(true);
        notificationChannel1.setSound(notification,audioAttributes);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(notificationChannel1);
        manager.createNotificationChannel(notificationChannel2);
    }

}
