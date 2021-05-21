package com.example.xnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.xnotes.services.BackgroundNotification;
import com.example.xnotes.services.MyServices;

public class StopForeground extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MyServices.class);
        stopService(intent);

        //close activity and services
        new BackgroundNotification(this).stopRunnable();
        MainActivity.onFinishActivity();
        MyServices.onFinishActivity();
        finish();

    }

}