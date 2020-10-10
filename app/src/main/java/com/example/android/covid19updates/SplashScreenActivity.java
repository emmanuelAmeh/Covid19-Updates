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
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(4000)
                .withBackgroundColor(Color.WHITE)
                .withAfterLogoText(getString(R.string.splashscreen_text))
                .withHeaderText(getString(R.string.splash_header))
                .withLogo(R.drawable.ic_local_hospital);

        View easySplashScreen = configSplash.create();
        setContentView(easySplashScreen);
    }
}