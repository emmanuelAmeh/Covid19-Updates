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
        // DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

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
