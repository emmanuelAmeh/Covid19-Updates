package com.example.android.covid19updates;

import retrofit2.Call;
import retrofit2.http.GET;

interface APIInterface {

    @GET("/countries")
    Call<CountryData> getCountryData ();

    @GET("/summary")
    Call<WorldData> getWorldData ();
}
