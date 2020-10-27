package com.example.android.covid19updates;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

class CountryData {
    @SerializedName("data")
    public List<Info> data = new ArrayList<>();

    static class Info implements Parcelable {
        @SerializedName("name")
        public String name;

        @SerializedName("population")
        public String population;

        @SerializedName("updated_at")
        public String updated_at;

        @SerializedName("today")
        public Today today = new Today();

        @SerializedName("latest_data")
        public LatestData latestData = new LatestData();

        class Today {
            @SerializedName("deaths")
            public String deaths;

            @SerializedName("confirmed")
            public String confirmed;
        }

        class LatestData {
            @SerializedName("deaths")
            public String deaths;

            @SerializedName("confirmed")
            public String confirmed;

            @SerializedName("recovered")
            public String recovered;

            @SerializedName("critical")
            public String critical;

            @SerializedName("calculated")
            public Calculated calculated = new Calculated();

            class Calculated {
                @SerializedName("death_rate")
                public String death_rate;

                @SerializedName("recovery_rate")
                public String recovery_rate;

                @SerializedName("recovered_vs_death_ratio")
                public String recovered_vs_death_ratio;

                @SerializedName("cases_per_million_population")
                public String cases_per_million_population;
            }
        }

        public Info (Parcel parcel){
            name = parcel.readString();
            population = parcel.readString();
            updated_at = parcel.readString();
            today.confirmed = parcel.readString();
            today.deaths = parcel.readString();
            latestData.deaths = parcel.readString();
            latestData.confirmed = parcel.readString();
            latestData.recovered = parcel.readString();
            latestData.critical = parcel.readString();
            latestData.calculated.death_rate = parcel.readString();
            latestData.calculated.recovery_rate = parcel.readString();
            latestData.calculated.recovered_vs_death_ratio = parcel.readString();
            latestData.calculated.cases_per_million_population = parcel.readString();
        }

        @Override
        public int describeContents () {
            return 0;
        }

        @Override
        public void writeToParcel (Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(population);
            dest.writeString(updated_at);
            dest.writeString(today.confirmed);
            dest.writeString(today.deaths);
            dest.writeString(latestData.deaths);
            dest.writeString(latestData.confirmed);
            dest.writeString(latestData.recovered);
            dest.writeString(latestData.critical);
            dest.writeString(latestData.calculated.death_rate);
            dest.writeString(latestData.calculated.recovery_rate);
            dest.writeString(latestData.calculated.recovered_vs_death_ratio);
            dest.writeString(latestData.calculated.cases_per_million_population);
        }

        public static final Creator<Info> CREATOR = new Creator<Info>(){
            @Override
            public Info createFromParcel (Parcel parcel) {
                return new Info(parcel);
            }

            @Override
            public Info[] newArray (int size) {
                return new Info[size];
            }
        };
    }
}
