package com.example.android.covid19updates;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerItems;
    APIInterface mAPIInterface;
    private CountryData mCountryData;
    private List<CountryData.Info> mInfoList;
    private List<String> mCountryNameList;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);
        getSupportActionBar().setTitle("Countries");

        mRecyclerItems = findViewById(R.id.list_countries);
        mInfoList = new ArrayList<>();
        mCountryNameList = new ArrayList<>();

        getCountryData();
    }

    private void getCountryData () {
        mAPIInterface = APIClient.getClient("https://corona-api.com").create(APIInterface.class);

        final Call<CountryData> countryData = mAPIInterface.getCountryData();
        countryData.enqueue(new Callback<CountryData>() {
            @Override
            public void onResponse (Call<CountryData> call, Response<CountryData> response) {
                mCountryData = response.body();

                for (CountryData.Info info : mCountryData.data) {
                    mCountryNameList.add(info.name);
                    mInfoList.add(info);
                }

                Collections.sort(mCountryNameList);
                initializeDisplayContent();

                Toast.makeText(getBaseContext(), "Successfully loaded countries data", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure (Call<CountryData> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initializeDisplayContent () {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        CountryListAdapter adapter = new CountryListAdapter(this, mInfoList, mCountryNameList);

        mRecyclerItems.setLayoutManager(layoutManager);
        mRecyclerItems.setAdapter(adapter);
    }
}