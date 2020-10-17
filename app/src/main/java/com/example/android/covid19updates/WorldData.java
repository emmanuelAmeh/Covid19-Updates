package com.example.android.covid19updates;

import com.google.gson.annotations.SerializedName;

class WorldData {
    @SerializedName("Global")
    public Global global = new Global();

    @SerializedName("Date")
    public String date;

    public class Global {
        @SerializedName("NewConfirmed")
        public String newConfirmed;

        @SerializedName("TotalConfirmed")
        public String totalConfirmed;

        @SerializedName("NewDeaths")
        public String newDeaths;

        @SerializedName("TotalDeaths")
        public String totalDeaths;

        @SerializedName("NewRecovered")
        public String newRecovered;

        @SerializedName("TotalRecovered")
        public String totalRecovered;
    }
}
