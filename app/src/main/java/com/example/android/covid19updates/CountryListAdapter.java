package com.example.android.covid19updates;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.ViewHolder>{
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final String COUNTRY_NAME = "com.example.android.covid19updates.COUNTRY_NAME";
    private final String COUNTRY_DATA = "com.example.android.covid19updates.COUNTRY_DATA";
    private List<CountryData.Info> mInfoList;
    private List<String> mCountryNameList;
    private String mCountryName;

    public CountryListAdapter (Context context, List<CountryData.Info> infoList, List<String> countryNameList){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mInfoList = infoList;
        mCountryNameList = countryNameList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.country_name, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
        mCountryName = mCountryNameList.get(position);

        holder.mTextCountryName.setText(mCountryName);
    }

    @Override
    public int getItemCount () {
        return mInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextCountryName;

        public ViewHolder (@NonNull final View itemView) {
            super(itemView);

            mTextCountryName = itemView.findViewById(R.id.text_country_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    final String countryName = mTextCountryName.getText().toString();
                    Intent detailIntent = new Intent(mContext, DetailActivity.class);

                    for (CountryData.Info info : mInfoList){
                        if (info.name.equals(countryName)){
                            detailIntent.putExtra(COUNTRY_DATA, info);
                        }
                    }

                    detailIntent.putExtra(COUNTRY_NAME, countryName);
                    mContext.startActivity(detailIntent);
                }
            });
        }
    }
}
