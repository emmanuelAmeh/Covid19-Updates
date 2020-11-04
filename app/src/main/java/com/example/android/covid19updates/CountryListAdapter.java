package com.example.android.covid19updates;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.ViewHolder> implements Filterable {
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final String COUNTRY_NAME = "com.example.android.covid19updates.COUNTRY_NAME";
    private final String COUNTRY_DATA = "com.example.android.covid19updates.COUNTRY_DATA";
    private List<CountryData.Info> mInfoList;
    private List<String> mCountryNameList;
    private List<String> mCountryNameListFull;
    private Filter mCountryNameListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mCountryNameListFull);
            } else {
                filteredList.clear();
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (String country : mCountryNameList) {
                    if (country.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(country);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mCountryNameList.clear();
            mCountryNameList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public CountryListAdapter(Context context, List<CountryData.Info> infoList, List<String> countryNameList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mInfoList = infoList;
        mCountryNameList = countryNameList;
        mCountryNameListFull = new ArrayList<>(mCountryNameList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.country_name, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String mCountryName = mCountryNameList.get(position);

        holder.mTextCountryName.setText(mCountryName);
    }

    @Override
    public int getItemCount() {
        return mCountryNameList.size();
    }

    //Implementing Country Filter
    @Override
    public Filter getFilter() {
        return mCountryNameListFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextCountryName;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            mTextCountryName = itemView.findViewById(R.id.tv_name_emmanuel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String countryName = mTextCountryName.getText().toString();
                    Intent detailIntent = new Intent(mContext, DetailActivity.class);

                    for (CountryData.Info info : mInfoList) {
                        if (info.name.equals(countryName)) {
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
