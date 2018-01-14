package com.example.ne.bmail.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.ne.bmail.ui.WelcomeActivity;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
        Intent mainIntent = new Intent (context, WelcomeActivity.class);
        mainIntent.putExtra("refresh_reminders", true);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity (mainIntent);
    }
}