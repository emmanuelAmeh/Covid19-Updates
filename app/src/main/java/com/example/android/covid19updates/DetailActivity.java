package com.example.android.covid19updates;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.android.covid19updates.FetchData.getFormattedDate;
import static com.example.android.covid19updates.FetchData.insertNumberComma;

public class DetailActivity extends AppCompatActivity {
    private final String COUNTRY_NAME = "com.example.android.covid19updates.COUNTRY_NAME";
    private final String COUNTRY_DATA = "com.example.android.covid19updates.COUNTRY_DATA";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        String countryNameString = getIntent().getStringExtra(COUNTRY_NAME);
        CountryData.Info countryData = getIntent().getParcelableExtra(COUNTRY_DATA);

        getSupportActionBar().setTitle(countryNameString);

        TextView countryName = findViewById(R.id.text_country_name);
        TextView totalCases = findViewById(R.id.tv_total_cases_value);
        TextView totalRecoveries = findViewById(R.id.tv_total_recoveries_value);
        TextView totalDeaths = findViewById(R.id.tv_total_deaths_value);
        TextView newCases = findViewById(R.id.tv_new_cases_value);
        TextView newDeaths = findViewById(R.id.tv_new_deaths_value);
        TextView updatedAt = findViewById(R.id.tv_updated_at_value);

        countryName.setText(countryNameString);
        totalCases.setText(insertNumberComma(countryData.latestData.confirmed));
        totalRecoveries.setText(insertNumberComma(countryData.latestData.recovered));
        totalDeaths.setText(insertNumberComma(countryData.latestData.deaths));
        newCases.setText(insertNumberComma(countryData.today.confirmed));
        newDeaths.setText(insertNumberComma(countryData.today.deaths));
        updatedAt.setText(getFormattedDate(countryData.updated_at));
    }
}