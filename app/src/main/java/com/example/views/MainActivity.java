package com.example.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joweather.R;

import java.util.ArrayList;

import com.example.viewmodel.JoWeatherViewModel;
import recyclerview.Adapter;
import com.example.model.City;
import weather.view.presenter.IContacter;

public class MainActivity extends AppCompatActivity implements IContacter.IMainView{

    private RecyclerView citiesList;
    private JoWeatherViewModel joViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instanstaite Realm database application

        initViews();

        // getting data from API and saving to database

     /*  joViewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())
                .create(JoWeatherViewModel.class);

        joViewModel.loadAndSaveWeatherInfo();*/


    }

    // initiate activity views
    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        citiesList = findViewById(R.id.cities_list);
        citiesList.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        citiesList.setHasFixedSize(true);

    }

    /**
     * receive the main activity data from(Model unit)
     *
     * @param cities ArrayList from model contains City the Application support
     * @implSpec setting the recycler view adapter with data received from model
     **/
    @Override
    public void setViewContent(ArrayList<City> cities) {
        Adapter adapter = new Adapter(cities, this, Adapter.CITY_ADAPTER);
        new Thread(() ->
                citiesList.post(() ->
                        citiesList.setAdapter(adapter)
                )
        ).start();
    }
}
