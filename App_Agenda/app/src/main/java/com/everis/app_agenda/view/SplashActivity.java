package com.everis.app_agenda.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.everis.app_agenda.R;

public class SplashActivity extends Activity {

    private static int SPLASH_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, ContactActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME);
    }
}
