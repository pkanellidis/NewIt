package com.example.oppai.top10.Fragments;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oppai.top10.Adapters.CountryRVAdapter;
import com.example.oppai.top10.Country;
import com.example.oppai.top10.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GlobalFragment extends Fragment {
    private String URL = "https://newsapi.org/v2/top-headlines";
    private ArrayList<Country> preferredCountries;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View Rootview = inflater.inflate(R.layout.fragment_main, container, false);

        //disable swipe to refresh layout
        SwipeRefreshLayout layout = Rootview.findViewById(R.id.SwipeRefresh);
        layout.setEnabled(false);
        layout.setRefreshing(false);

        RecyclerView recyclerView = Rootview.findViewById(R.id.RecyclerViewMain);
        preferredCountries = new ArrayList<>();

        //Get country Data from strings.xml
        List<String> countryCodes = Arrays.asList(getActivity().getResources().getStringArray(R.array.Country_Codes));
        List<String> countryUrls = Arrays.asList(getActivity().getResources().getStringArray(R.array.country_Code_Urls));
        List<String> countryNames = Arrays.asList(getResources().getStringArray(R.array.Country_Code_values));

        //Get preferred countries
        int i = 0;
        for(String countryCode : countryCodes){


            boolean isEnabled = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(countryCode, false);

            if (isEnabled) {
                Country country = new Country(countryNames.get(i), countryUrls.get(i), countryCode);
                preferredCountries.add(country);
            }
            i++;

        }

        CountryRVAdapter rvAdapter = new CountryRVAdapter(preferredCountries, getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(rvAdapter);




        return Rootview;
    }



}
