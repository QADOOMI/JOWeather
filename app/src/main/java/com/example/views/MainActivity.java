package com.example.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.joweather.R;
import com.example.joweather.databinding.ActivityMainBinding;
import com.example.viewmodel.JoWeatherViewModel;
import com.example.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

import app.WeatherApp;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView citiesList;
    private Group errorGroup;
    private AppCompatTextView errorTitle;

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

        mainViewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(WeatherApp.getThis())
                .create(MainActivityViewModel.class);

        joWeatherViewModel = JoWeatherViewModel.getInstance(WeatherApp.getThis());

        joWeatherViewModel.loadAndSaveWeatherInfo();

        joWeatherViewModel.getErrorHappen().observe(this, weatherError -> {
            if (weatherError != null) {
                errorGroup.setVisibility(View.VISIBLE);
                if (weatherError.getMessage() != null
                        || !weatherError.getMessage().isEmpty()) {
                    if (swipeRefreshLayout.isRefreshing())
                        swipeRefreshLayout.setRefreshing(false);

                    if (weatherError.getMessage().contains("Unable to resolve host")) {
                        errorTitle.setText(R.string.check_internet_msg);
                        return;
                    }
                }
                errorTitle.setText(weatherError.getMessage());
            }
        });

        joWeatherViewModel.getWeathers().observe(this, realmWeathers -> {
            Log.e(MainActivity.class.getSimpleName(), "onCreate: " + realmWeathers.toString());
            if (errorGroup.getVisibility() == View.VISIBLE)
                errorGroup.setVisibility(View.GONE);

            if (realmWeathers.size() > 0) {
                adapter.addOrUpdateCities(new ArrayList<>(realmWeathers));
            }

            if (swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (pause) {
            mainViewModel.fetchWeatherInfo();
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
        errorGroup = binding.errorGroup;
        errorTitle = binding.errorTitle;

        swipeRefreshLayout = binding.swiperefresh;
        swipeRefreshLayout.setOnRefreshListener(() -> joWeatherViewModel.loadAndSaveWeatherInfo());

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
