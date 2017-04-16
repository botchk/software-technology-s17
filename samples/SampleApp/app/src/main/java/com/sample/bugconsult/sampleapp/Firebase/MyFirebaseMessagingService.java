package com.sample.bugconsult.sampleapp.Firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sample.bugconsult.sampleapp.NotificationActivity;
import com.sample.bugconsult.sampleapp.R;

/**
 * Created by joe on 16.04.17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static int notification_id = 0; // can be used to update the notification later

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // NOTES:
        // - Message has to be handled within 10 seconds !
        // - FCM distinguishes 2 types of messages: notifications and data
        //   Notification messages DO NOT trigger this method when app is in background!
        //   i.e. click on notification in system tray will just display main activity -> data messages only?

        Log.d("Firebase", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("Firebase", "Message data payload: " + remoteMessage.getData());

            // create notification
            showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("Firebase", "Message Notification Body: " + remoteMessage.getNotification().getBody());

            // create notification
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }

    private void showNotification(String title, String body) {
        // create "click action" i.e. open another activity while saving the current (back button returns to current activity)
        Intent result_intent = new Intent(this, NotificationActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .putExtra("title", title) // parameters to the activity
                .putExtra("body", body);

        // notification_id needed otherwise multiple notifications would refer to the same intent
        PendingIntent result_pending_intent = PendingIntent.getActivity(this, MyFirebaseMessagingService.notification_id, result_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // create a notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentIntent(result_pending_intent)
                .setAutoCancel(true); // remove the notification

        Log.d("Firebase", "ID: " + MyFirebaseMessagingService.notification_id);
        MyFirebaseMessagingService.notification_id++; // unique id for multiple notifications

        // build notification and show it
        NotificationManager not_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        not_manager.notify(MyFirebaseMessagingService.notification_id, builder.build());
    }

    @Override
    public void onDeletedMessages() {
        // usually only called when too many messages are pending
        Log.d("Firebase", "Messages deleted.");
    }
}
