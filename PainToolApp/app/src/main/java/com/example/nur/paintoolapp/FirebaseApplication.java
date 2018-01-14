package com.example.nur.paintoolapp;

import android.app.Application;
import com.firebase.client.Firebase;

public class FirebaseApplication extends Application{
    /**
     * @author  Nur Imtiazul Haque
     * @author  Tasfia Mashiat
     * @version 1.0
     * @since   2016-16-06
     * This class is shares by all classes
     * */

    @Override
    public void onCreate() {
        super.onCreate();
        /* Initialize Firebase */
        Firebase.setAndroidContext(this);
    }
}
