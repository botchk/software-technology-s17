package com.sample.bugconsult.sampleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {

    private TextView tv_title;
    private TextView tv_body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        // create link with textviews
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        this.tv_body = (TextView) findViewById(R.id.tv_body);

        // change textviews according to parameters
        this.onNewIntent(this.getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        if (extras != null) {
            Log.d("Firebase", "Title: " + extras.getString("title"));
            Log.d("Firebase", "Body: " + extras.getString("body"));

            this.tv_title.setText(extras.getString("title"));
            this.tv_body.setText(extras.getString("body"));
        }
    }
}
