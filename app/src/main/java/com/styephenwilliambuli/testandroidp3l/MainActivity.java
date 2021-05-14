package com.styephenwilliambuli.testandroidp3l;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {
            Intent splash = new Intent(MainActivity.this, QRActivity.class);
            startActivity(splash);
            finish();
        }, SPLASH_TIME_OUT);
    }
}