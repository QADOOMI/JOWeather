package com.example.views;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joweather.databinding.ActivityMainBinding;
import com.example.model.City;
import com.example.viewmodel.JoWeatherViewModel;
import com.example.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

import app.WeatherApp;

public class MainActivity extends AppCompatActivity {

    private RecyclerView citiesList;

    private MainActivityViewModel mainViewModel;
    private JoWeatherViewModel joWeatherViewModel;

    private ActivityMainBinding binding;
    private CitiesAdapter adapter;

    private boolean pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // instanstaite Realm database application

        initViews();

        // getting data from API and saving to database

     /*  joViewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())
                .create(JoWeatherViewModel.class);

        joViewModel.loadAndSaveWeatherInfo();*/

        mainViewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(WeatherApp.getThis())
                .create(MainActivityViewModel.class);
        joWeatherViewModel = JoWeatherViewModel.getInstance(WeatherApp.getThis());

        mainViewModel.getCityWeather().observe(this, realmWeathers -> {
            if (realmWeathers.size() > 0) {
                adapter.addOrUpdateCities(realmWeathers);
            }
        });

        mainViewModel.fetchWeatherInfo();


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (pause) {
            joWeatherViewModel.loadAndSaveWeatherInfo();
            pause = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause = true;
    }

    // initiate activity views
    private void initViews() {
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        citiesList = binding.citiesList;
        citiesList.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        citiesList.setHasFixedSize(true);

        adapter = new CitiesAdapter(new ArrayList<>(), this);
        citiesList.post(() ->
                citiesList.setAdapter(adapter));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
