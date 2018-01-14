package com.example.nur.monglaport.Class;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.firebase.client.Firebase;

/**
 * Created by nur on 11/16/16.
 */

public class AppClass extends Application {

    //this will initialize multidex in your own Application class
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        Firebase.setAndroidContext(this);
    }
}