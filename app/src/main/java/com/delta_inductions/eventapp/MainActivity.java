package com.delta_inductions.eventapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{
    private NotificationManagerCompat managerCompat;
    private static final String TAG = "MainActivity";
    long elapsed = 15*60*1000;
    private TextView textView;
    private EditText Eventname;
    private EditText Eventmessage;
    private String eventname;
    private String eventmessage;
    private PendingIntent pendingIntent2;
    private Button setevent;
    private Button cancelevent;
    private PendingIntent pendingIntent1;
    private AlarmManager  alarmManager;
    private Receiver2 receiver2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        managerCompat = NotificationManagerCompat.from(this);
        Eventname = findViewById(R.id.eventname);
        Eventmessage = findViewById(R.id.eventmessage);
        textView = findViewById(R.id.textView);
        setevent = findViewById(R.id.setevent);
        cancelevent = findViewById(R.id.cancelevent);
        setevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Eventname.getText().toString().isEmpty()&&!Eventmessage.getText().toString().isEmpty()) {

                    DialogFragment timePicker = new TimePickerfrag();
                    timePicker.show(getSupportFragmentManager(), "time picker");
                }
                else
                {
                    {
                        Toast.makeText(MainActivity.this, "Fill the details", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        cancelevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             canceleventfun();
            }
        });

    }

    private void canceleventfun() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent1);
        alarmManager.cancel(pendingIntent2);
        textView.setText("Event canceled");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND,0);
        updatetext(c);
        startEvent(c);
        
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startEvent(Calendar c) {
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if(!(System.currentTimeMillis() > c.getTimeInMillis() - elapsed)) {
            Intent intent1 = new Intent(this, Receiver.class);
            eventname = Eventname.getText().toString();
            eventmessage = Eventmessage.getText().toString();
            intent1.putExtra("title", eventname);
            intent1.putExtra("message", eventmessage);
            Log.d(TAG, "startAlarm: " + eventmessage + eventname);
            pendingIntent1 = PendingIntent.getBroadcast(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            manager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() - elapsed, pendingIntent1);
        }
        Intent intent2 = new Intent(this, Receiver2.class);
        eventname = Eventname.getText().toString();
        eventmessage  = Eventmessage.getText().toString();
        intent2.putExtra("title",eventname);
        intent2.putExtra("message",eventmessage);
        pendingIntent2 = PendingIntent.getBroadcast(this, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        manager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent2);

    }

    private void updatetext(Calendar c) {
        String timeText = "Event set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        textView.setText(timeText);
    }

}