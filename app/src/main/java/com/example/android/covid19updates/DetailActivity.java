package com.example.android.covid19updates;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import static com.example.android.covid19updates.FetchData.getFormattedDate;
import static com.example.android.covid19updates.FetchData.insertNumberComma;

public class DetailActivity extends AppCompatActivity {
    private final String COUNTRY_NAME = "com.example.android.covid19updates.COUNTRY_NAME";
    private final String COUNTRY_DATA = "com.example.android.covid19updates.COUNTRY_DATA";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String countryNameString = getIntent().getStringExtra(COUNTRY_NAME);
        CountryData.Info countryData = getIntent().getParcelableExtra(COUNTRY_DATA);

        Objects.requireNonNull(getSupportActionBar()).setTitle(countryNameString);

        TextView countryName = findViewById(R.id.tv_country_name);
        TextView totalCases = findViewById(R.id.tv_country_cc_value);
        TextView totalRecoveries = findViewById(R.id.tv_country_rc_value);
        TextView totalDeaths = findViewById(R.id.tv_country_death_value);
        TextView newCases = findViewById(R.id.tv_country_cc_value_today);
        TextView newDeaths = findViewById(R.id.tv_country_death_value_today);
        TextView updatedAt = findViewById(R.id.tv_country_updated_at);
        TextView countryPopulation = findViewById(R.id.tv_country_population_value);
        TextView casePerMillion = findViewById(R.id.tv_country_cpm_value);
        TextView recoveryRate = findViewById(R.id.tv_country_recovery_rate_value);
        TextView deathRate = findViewById(R.id.tv_country_deathrate_value);

        countryName.setText(countryNameString);
        if (countryData != null) {
            totalCases.setText(insertNumberComma(countryData.latestData.confirmed));
            totalRecoveries.setText(insertNumberComma(countryData.latestData.recovered));
            totalDeaths.setText(insertNumberComma(countryData.latestData.deaths));
            newCases.setText(insertNumberComma(countryData.today.confirmed));
            newDeaths.setText(insertNumberComma(countryData.today.deaths));
            updatedAt.setText(getFormattedDate(countryData.updated_at));
            countryPopulation.setText(insertNumberComma(countryData.population));
            casePerMillion.setText(insertNumberComma(countryData.latestData.calculated.cases_per_million_population));
            recoveryRate.setText(insertNumberComma(countryData.latestData.calculated.recovery_rate));
            deathRate.setText(insertNumberComma(countryData.latestData.calculated.death_rate));
        }

    }
}