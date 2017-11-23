package com.example.ajays.firelogin2;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by ajays on 1/5/2017.
 */

public class FireLogin2 extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
