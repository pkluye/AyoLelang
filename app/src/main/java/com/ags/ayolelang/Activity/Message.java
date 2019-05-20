package com.ags.ayolelang.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ags.ayolelang.R;

public class Message extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        //more info https://blog.sendbird.com/android-chat-tutorial-building-a-messaging-ui
    }
}
