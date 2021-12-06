package com.tonychau.restaurantrandomizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UserActivity extends AppCompatActivity {

    public static boolean LOGGED_IN = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }
}