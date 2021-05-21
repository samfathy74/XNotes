package com.example.xnotes.services;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.xnotes.MainActivity;
import com.example.xnotes.NoteEditorActivity;
import com.example.xnotes.R;
import com.example.xnotes.StopForeground;

public class MyServices extends Service {
    public static Service service;
    private final IBinder MyBounder = new MyLocalBinder();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        service = this;
        createNotificationChannel();

        Intent intent1 = new Intent(this, NoteEditorActivity.class);
        PendingIntent pendingIntentNote = PendingIntent.getActivity(this, 0, intent1, 0);

        Intent intent2 = new Intent(this, StopForeground.class);
        PendingIntent stopPendingIndent = PendingIntent.getActivity(this, 0, intent2, 0);

        Intent intent3 = new Intent(this, MainActivity.class);
        PendingIntent mainPendingIndent = PendingIntent.getActivity(this, 0, intent3, 0);

        Notification notification = new NotificationCompat.Builder(this, "FOREGROUND")
                .setContentText("XNotes App Running Now")
                .setContentTitle("XNotes")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(mainPendingIndent)
                .addAction(R.drawable.ic_create, "Add Note", pendingIntentNote)
                .addAction(R.drawable.ic_clear, "Close", stopPendingIndent)
                .build();

        startForeground(1, notification);
        return START_STICKY;
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel myChannel = new NotificationChannel(
                    "FOREGROUND", "FOREGROUND CHANNEL", NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannel(myChannel);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return MyBounder;
    }

    //exit from services
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        stopSelf();
    }

    @Override
    public void onCreate() {
        new BackgroundNotification(this).performNotification(this, 1 * 60 * 1000); //900000~15 min
        super.onCreate();
    }

    public class MyLocalBinder extends Binder {
        public MyServices getServices() {
            return MyServices.this;
        }
    }

//    public void stopServicesWithBound() {
//        stopForeground(true);
//        stopSelf();
//    }

    //to stop foreground ser
    public static void onFinishActivity() {
        MyServices.service.stopSelf();
        MyServices.service.stopForeground(true);
    }
}
