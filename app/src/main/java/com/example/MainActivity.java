package com.example;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joweather.R;

import java.util.ArrayList;

import io.realm.Realm;
import mainpage.view.presenter.MainActivityPresenter;
import recyclerview.Adapter;
import recyclerview.City;
import weather.view.presenter.IContacter;

public class MainActivity extends AppCompatActivity implements IContacter.IMainView{

    private RecyclerView citiesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instanstaite Realm database application
        Realm.init(getApplicationContext());

        initViews();

        // getting data from API and saving to database
        IContacter.IPresenter presenter = new MainActivityPresenter(this);
        presenter.onWeatherDataLoaded();


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
