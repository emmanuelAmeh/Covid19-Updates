package com.example.android.covid19updates;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        getSupportActionBar().setTitle("Credits");
    }
}