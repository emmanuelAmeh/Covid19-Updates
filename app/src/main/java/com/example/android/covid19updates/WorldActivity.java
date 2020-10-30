package com.example.android.covid19updates;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.covid19updates.FetchData.getFormattedDate;
import static com.example.android.covid19updates.FetchData.insertNumberComma;

public class WorldActivity extends AppCompatActivity {
    APIInterface mAPIInterface;
    private WorldData.Global mData;
    private String mUpdateDate;
    private String mShareMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);

        mShareMessage = "Worldwide Coronavirus Case Data";
        getWorldData();
    }

    // get data from API url
    private void getWorldData () {
        mAPIInterface = APIClient.getClient().create(APIInterface.class);

        final Call<WorldData> worldData = mAPIInterface.getWorldData();
        worldData.enqueue(new Callback<WorldData>() {
            @Override
            public void onResponse (Call<WorldData> call, Response<WorldData> response) {
                WorldData data = response.body();
                mData = data.global;
                mUpdateDate = data.date;
                Log.e("GetResponse", String.valueOf(response.code()));
                Toast.makeText(getBaseContext(), "Successfully loaded world data", Toast.LENGTH_LONG).show();

                initializeDisplayContent();
            }

            @Override
            public void onFailure (Call<WorldData> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();
                Log.e("GetResponse", Objects.requireNonNull(t.getMessage()));
                t.printStackTrace();
            }
        });
    }

    // sets textviews for each data point
    private void initializeDisplayContent () {
        TextView updatedDate = findViewById(R.id.tv_country_updated_at);
        TextView totalCases = findViewById(R.id.tv_worlddata_cc_value);
        TextView totalRecoveries = findViewById(R.id.tv_worlddata_rc_value);
        TextView activeCases = findViewById(R.id.tv_worlddata_ac_value);
        TextView totalDeaths = findViewById(R.id.tv_worlddata_death_value);

        String updateDate = getFormattedDate(mUpdateDate);
        String updateMessage = updatedDate.getText().toString() + "\n" + updateDate;

        int totalCasesValue = Integer.parseInt(mData.totalConfirmed);
        int totalRecoveriesValue = Integer.parseInt(mData.totalRecovered);
        int totalDeathsValue = Integer.parseInt(mData.totalDeaths);
        int activeCasesValue = totalCasesValue - totalRecoveriesValue - totalDeathsValue;

        String totalCasesString = insertNumberComma(mData.totalConfirmed);
        String totalRecoveriesString = insertNumberComma(mData.totalRecovered);
        String totalActiveString = insertNumberComma(String.valueOf(activeCasesValue));
        String totalDeathsString = insertNumberComma(mData.totalDeaths);

        updatedDate.setText(updateMessage);
        totalCases.setText(totalCasesString);
        totalRecoveries.setText(totalRecoveriesString);
        activeCases.setText(totalActiveString);
        totalDeaths.setText(totalDeathsString);

        mShareMessage = "Worldwide COVID-19 Case Data on " + "\n"
                + updateDate + "\n\n"
                + getString(R.string.confirmed_cases) + ": " + totalCasesString + "\n"
                + getString(R.string.recovered_cases) + ": " + totalRecoveriesString + "\n"
                + getString(R.string.active_cases) + ": " + totalActiveString + "\n"
                + getString(R.string.deaths) + ": " + totalDeathsString + "\n\n"
                + "Source: WHO" + "\n"
                + "Powered by: COVID19 Updates App" + "\n";
    }

    public void openCountryListActivity(View view) {
        Intent intent = new Intent(this, CountryListActivity.class);
        startActivity(intent);
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