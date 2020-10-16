package com.example.android.covid19updates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class WorldActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);
    }

    public void openCountryListActivity(View view) {
        Intent intent = new Intent(this, CountryListActivitiy.class);
        startActivity(intent);
    }
}