package com.example.android.covid19updates;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        EasySplashScreen configSplash = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(WorldActivity.class)
                .withSplashTimeOut(4000)
                .withBackgroundColor(Color.WHITE)
                .withAfterLogoText(getString(R.string.splashscreen_text))
                .withLogo(R.drawable.app_logo);

        View easySplashScreen = configSplash.create();
        setContentView(easySplashScreen);
    }
}