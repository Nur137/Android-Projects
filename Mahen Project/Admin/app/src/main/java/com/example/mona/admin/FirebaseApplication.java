package com.example.mona.admin;

import android.app.Application;

import com.firebase.client.Firebase;



public class FirebaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
