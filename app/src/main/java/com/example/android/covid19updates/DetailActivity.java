package com.example.android.covid19updates;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.Objects;

import static com.example.android.covid19updates.FetchData.getFormattedDate;
import static com.example.android.covid19updates.FetchData.insertNumberComma;

public class DetailActivity extends AppCompatActivity {
    private final String COUNTRY_NAME = "com.example.android.covid19updates.COUNTRY_NAME";
    private final String COUNTRY_DATA = "com.example.android.covid19updates.COUNTRY_DATA";
    private String mShareMessage;

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
        TextView totalActiveCases = findViewById(R.id.tv_country_ac_value);
        TextView totalDeaths = findViewById(R.id.tv_country_death_value);
        TextView newCases = findViewById(R.id.tv_country_cc_value_today);
        TextView newDeaths = findViewById(R.id.tv_country_death_value_today);
        TextView updatedAt = findViewById(R.id.tv_country_updated_at);
        TextView countryPopulation = findViewById(R.id.tv_country_population_value);
        TextView casePerMillion = findViewById(R.id.tv_country_cpm_value);
        TextView recoveryRate = findViewById(R.id.tv_country_recovery_rate_value);
        TextView deathRate = findViewById(R.id.tv_country_deathrate_value);

        int totalCasesValue = Integer.parseInt(countryData.latestData.confirmed);
        int totalRecoveriesValue = Integer.parseInt(countryData.latestData.recovered);
        int totalDeathsValue = Integer.parseInt(countryData.latestData.deaths);
        int activeCasesValue = totalCasesValue - totalRecoveriesValue - totalDeathsValue;

        String populationString = insertNumberComma(countryData.population);
        String totalCasesString = insertNumberComma(countryData.latestData.confirmed);
        String totalRecoveriesString = insertNumberComma(countryData.latestData.recovered);
        String totalActiveString = insertNumberComma(String.valueOf(activeCasesValue));
        String totalDeathsString = insertNumberComma(countryData.latestData.deaths);
        String newCasesString = insertNumberComma(countryData.today.confirmed);
        String newDeathsString = insertNumberComma(countryData.today.deaths);
        String updatedAtString = getFormattedDate(countryData.updated_at);
        String casesPerMillionString = insertNumberComma(countryData.latestData.calculated.cases_per_million_population);
        String recoveryRateString = String.format(countryData.latestData.calculated.recovery_rate, "%.2f");
        String deathRateString = String.format(countryData.latestData.calculated.death_rate, "%.2f");

        double recoveryRateDouble = Double.parseDouble(recoveryRateString);
        double deathRateDouble = Double.parseDouble(deathRateString);

        DecimalFormat precision = new DecimalFormat("0.00");

        recoveryRateString = precision.format(recoveryRateDouble);
        deathRateString = precision.format(deathRateDouble);

        countryName.setText(countryNameString);
        if (countryData != null) {
            totalCases.setText(totalCasesString);
            totalRecoveries.setText(totalRecoveriesString);
            totalActiveCases.setText(totalActiveString);
            totalDeaths.setText(totalDeathsString);
            newCases.setText(newCasesString);
            newDeaths.setText(newDeathsString);
            updatedAt.setText(updatedAtString);
            countryPopulation.setText(populationString);
            casePerMillion.setText(casesPerMillionString);
            recoveryRate.setText(recoveryRateString);
            deathRate.setText(deathRateString);
        }

        mShareMessage = countryNameString + " COVID-19 Case Data  on " + "\n"
                + updatedAtString + "\n\n"
                + getString(R.string.tv_country_popn) + ": " + populationString + "\n\n"
                + "New " + getString(R.string.confirmed_cases) + ": " + newCasesString + "\n"
                + "New " + getString(R.string.deaths) + ": " + newDeathsString + "\n\n"

                + "Total " + getString(R.string.confirmed_cases) + ": " + totalCasesString + "\n"
                + "Total " + getString(R.string.recovered_cases) + ": " + totalRecoveriesString + "\n"
                + "Total " + getString(R.string.active_cases) + ": " + totalActiveString + "\n"
                + "Total " + getString(R.string.deaths) + ": " + totalDeathsString + "\n\n"

                + getString(R.string.recovery_rate) + ": " + recoveryRateString + "\n"
                + getString(R.string.death_rate) + ": " + deathRateString + "\n\n"
                + getString(R.string.cases_per_million) + ": " + casesPerMillionString + "\n\n"
                + "Source: WHO" + "\n"
                + "Powered by: COVID19 Updates App" + "\n";
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.credits, menu);
        inflater.inflate(R.menu.menu_share, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.credits_action) {
            Intent creditsIntent = new Intent(this, CreditsActivity.class);
            startActivity(creditsIntent);
        } else if (id == R.id.share_action){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, mShareMessage);
            startActivity(shareIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}