package com.myapp.royalcounselling;


import androidx.fragment.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.myapp.royalcounselling.ui.PPTRequestFragment;

public class SplashScreenActivity extends AppCompatActivity {

    String email, password, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("MYAPP", MODE_PRIVATE);
        name = sharedPreferences.getString("KEY_NAME", "");
        email = sharedPreferences.getString("KEY_EMAIL", "");
        password = sharedPreferences.getString("KEY_PASSWORD", "");
        int time = 3000;
        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(i);
            Intent intent;
            if (password.equals("") && email.equals("")) {
                intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            } else {
                intent = new Intent(SplashScreenActivity.this, NavigationDrawerActivity.class);
            }
            startActivity(intent);
        }, time);
    }
    }