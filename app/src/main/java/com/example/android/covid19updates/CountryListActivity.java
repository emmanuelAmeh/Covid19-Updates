package com.example.android.covid19updates;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private CountryListAdapter mAdapter;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    private void initializeDisplayContent() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mAdapter = new CountryListAdapter(this, mInfoList, mCountryNameList);
        mRecyclerItems.setLayoutManager(layoutManager);
        mRecyclerItems.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    //implementing SearchView
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryRefinementEnabled(true);
        mSearchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                // mAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        String searchText = (String) mSearchView.getQuery();
        if (!mSearchView.isIconified() && mSearchView != null) {
            mSearchView.setIconified(true);
            mSearchView.onActionViewCollapsed();
            mSearchView.setQuery(searchText, true);
        } else {
            super.onBackPressed();
        }

    }


}