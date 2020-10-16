package com.example.android.covid19updates;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.covid19updates.FetchData.insertNumberComma;

public class WorldActivity extends AppCompatActivity {
    APIInterface mAPIInterface;
    private WorldData.Global mData;
    private String mUpdateDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);

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
                Log.e("GetResponse", t.getMessage());
                t.printStackTrace();
            }
        });
    }

    // sets textviews for each data point
    private void initializeDisplayContent () {
        TextView updatedDate = findViewById(R.id.tv_worlddata_date);
        TextView totalCases = findViewById(R.id.tv_worlddata_cc_value);
        TextView totalRecoveries = findViewById(R.id.tv_worlddata_rc_value);
        TextView activeCases = findViewById(R.id.tv_worlddata_ac_value);
        TextView totalDeaths = findViewById(R.id.tv_worlddata_death_value);
        /*TextView newCases = findViewById(R.id.text_new_cases);
        TextView newRecoveries = findViewById(R.id.text_new_recoveries);
        TextView newDeaths = findViewById(R.id.text_new_deaths);*/

        String updateMessage = updatedDate.getText().toString() + mUpdateDate;
        int totalCasesValue = Integer.parseInt(mData.totalConfirmed);
        int totalRecoveriesValue = Integer.parseInt(mData.totalRecovered);
        int totalDeathsValue = Integer.parseInt(mData.totalDeaths);
        int activeCasesValue = totalCasesValue - totalRecoveriesValue - totalDeathsValue;

        updatedDate.setText(updateMessage);
        totalCases.setText(insertNumberComma(mData.totalConfirmed));
        totalRecoveries.setText(insertNumberComma(mData.totalRecovered));
        activeCases.setText(insertNumberComma(String.valueOf(activeCasesValue)));
        totalDeaths.setText(insertNumberComma(mData.totalDeaths));
        /*newCases.setText(insertNumberComma(mData.newConfirmed));
        newRecoveries.setText(insertNumberComma(mData.newRecovered));
        newDeaths.setText(insertNumberComma(mData.newDeaths));*/

        Log.d("formatNumber", insertNumberComma(mData.totalConfirmed));
    }

    public void openCountryListActivity(View view) {
        Intent intent = new Intent(this, CountryListActivitiy.class);
        startActivity(intent);
    }
}