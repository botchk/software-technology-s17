package com.sample.bugconsult.sampleapp.Firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by joe on 16.04.17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Firebase", "Refreshed token: " + refreshedToken);

        /*
        NOTE: Token is only refreshed under special cirumstances => best to send token on login.
        Token can be used to send messages to specific users/devices.
         */

        // subscribe to a topic
        FirebaseMessaging.getInstance().subscribeToTopic("test");
    }
}
