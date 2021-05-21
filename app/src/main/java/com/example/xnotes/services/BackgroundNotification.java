package com.example.xnotes.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.xnotes.MainActivity;
import com.example.xnotes.R;

public class BackgroundNotification {
    Context context;
    BackgroundTask message = new BackgroundTask();
    boolean isBound;
    public static MyServices boundServices;
    Runnable runnable;
    Handler handler = new Handler();
    private boolean killMe = false;
    NotificationManagerCompat managerCompat;
    Notification notification;
    String body;

    //constructor
    public BackgroundNotification(Context context) {
        this.context = context;
    }


    //start notification foreground
    public void performForegroundServices() {
        Intent intent = new Intent(context, MyServices.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);  //foreground services
        } else {
            context.startService(intent);
        }
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);  //bound services
    }

    //start notification background
    public void performNotification(final Context context, final int timeNotification) {
        //to open when click on notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel myChannel = new NotificationChannel("BACKGROUND", "BACKGROUND TASK", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannel(myChannel);

            runnable = new Runnable() {
                @Override
                public void run() {
                    myRunnable();
                }
            };

        } else {
            runnable = new Runnable() {
                @Override
                public void run() {
                    myRunnable();
                }
            };

        }
        if (!killMe) {
            runnable.run();
            handler.postDelayed(runnable, timeNotification);
        }
    }

    public void stopRunnable() {
        handler.removeCallbacks(runnable);
        killMe = true;
    }

    //bound
    public final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyServices.MyLocalBinder binder = (MyServices.MyLocalBinder) iBinder;
            boundServices = binder.getServices();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };


    private void myRunnable() {
        Intent intent1 = new Intent(context, MainActivity.class);
        final PendingIntent pendingIntentNote = PendingIntent.getActivity(context, 0, intent1, 0);

        body = message.getRandomMessage();
        notification = new NotificationCompat.Builder(context, "BACKGROUND")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("XNotes")
                .setContentText(body)
                .setContentIntent(pendingIntentNote)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setWhen(System.currentTimeMillis())
                .setLights(Color.GREEN, 3000, 3000)
                .setOnlyAlertOnce(false)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .build();

        managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(0, notification);

    }
}
