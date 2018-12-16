package com.tn.blasti.app;

import android.app.Application;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by amine 15/12/2018.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseMessaging.getInstance().subscribeToTopic("wpnewsnotification");
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

    }
}
