package com.example.android.covid19updates;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class FetchData {
    APIInterface mAPIInterface;
    private WorldData.Global mData;
    private Context mContext;
    // private CountryData mCountryData;
    private List<String> mCountryNameList;
    // private List<CountryData.Info> mInfoList;

    FetchData(Context context){
        mContext = context;
        mData = null;
        mCountryNameList = new ArrayList<>();
        // mInfoList = new ArrayList<>();
    }

    public WorldData.Global getWorldData () {
        mAPIInterface = APIClient.getClient().create(APIInterface.class);

        final Call<WorldData> worldData = mAPIInterface.getWorldData();
        worldData.enqueue(new Callback<WorldData>() {
            @Override
            public void onResponse (Call<WorldData> call, Response<WorldData> response) {
                WorldData data = response.body();
                mData = data.global;
                Log.e("GetResponse", String.valueOf(response.code()));
                Toast.makeText(mContext, "Successfully loaded world data", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure (Call<WorldData> call, Throwable t) {
                Toast.makeText(mContext, "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();
                Log.e("GetResponse", t.getMessage());
                t.printStackTrace();
            }
        });

        return mData;
    }

    /*public List<CountryData.Info> getCountryData () {
        mAPIInterface = APIClient.getClient().create(APIInterface.class);

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

                Toast.makeText(mContext, "Successfully loaded countries data", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure (Call<CountryData> call, Throwable t) {
                Toast.makeText(mContext, "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();
            }
        });

        return mInfoList;
    }*/

    // formats the nunbers with commas(,) every three digits from the right
    public static String insertNumberComma (String number){
        for (int i = number.length() - 3; i > 0; i -= 3){
            String firstPart = number.substring(0, i);
            String secondPart = number.substring(i);
            number = firstPart + "," + secondPart;
        }

        return number;
    }

    // formats the String from the API
    public static String getFormattedDate (String inputDate){
        DateFormat dateFormat = DateFormat.getDateTimeInstance();

        Date date = new Date();
        try {
            date = dateFormat.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMMM, yyyy 'at' HH:mm:ss");
        String dateString = outputFormat.format(date);

        return dateString;
    }
}
