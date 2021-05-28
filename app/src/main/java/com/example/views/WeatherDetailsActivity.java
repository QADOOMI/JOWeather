package com.example.views;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joweather.R;

import java.util.ArrayList;

import recyclerview.Adapter;
import com.example.model.City;
import com.example.model.WeatherItem;
import com.example.database.realm.RealmCreator;
import com.example.database.model.Clouds;
import com.example.database.model.Main;
import com.example.database.model.WeatherResponse;
import com.example.database.model.Wind;
import weather.view.presenter.IContacter;
import weather.view.presenter.WeatherPresenter;

public class WeatherDetailsActivity extends AppCompatActivity implements IContacter.IView{
    private ImageView cityImage;
    private TextView cityName, weatherState, temp;
    private ContentLoadingProgressBar progressBar;
    private RecyclerView infoList;

    private static final String TAG = WeatherDetailsActivity.class.getSimpleName();


    /**
     * get the sent data from MainActivity
     * @implNote send it to the presenter to load city's weather data from the API
     *
     */
    @Override
    protected void onCreate(Bundle savedInstance) {
        // enable transition
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_weather_details);

        initViews();
        progressBar.show();

        if (getIntent() != null) {
            new Thread(() ->
                    cityImage.post(() ->
                            cityImage.setImageDrawable(getResources().getDrawable(
                                    getIntent().getExtras().getInt(City.class.getSimpleName()), null)
                            ))).start();
            IContacter.IPresenter weatherPresenter =
                    new WeatherPresenter(this, getIntent().getExtras().getString(
                            City.Constants.REQUESTED_CITY));
            weatherPresenter.onWeatherDataLoaded();
        } else {
            Log.e(TAG, "onCreate -> WeatherDetailsActivity: getIntent() is null");
        }
    }

    // initiate activity views
    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add navigation button to the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressBar);

        cityImage = findViewById(R.id.city_image);
        cityName = findViewById(R.id.city_name);
        weatherState = findViewById(R.id.weather_desc);
        temp = findViewById(R.id.city_temp);

        infoList = findViewById(R.id.weather_details_info_list);
        infoList.setHasFixedSize(true);
        infoList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }


    /**
     * receive city weather data from(Model unit)
     *
     * @param weatherResponse is serialized object contains data from loaded from the API
     * @implSpec setting the recycler view adapter with data received from presenter
     **/
    @Override
    public void setViewContent(WeatherResponse weatherResponse) {
        progressBar.hide();

        ArrayList<WeatherItem> weatherItems = new ArrayList<>();

        weatherItems.add(new WeatherItem(Wind.class, new Wind(weatherResponse.getWind().getSpeed(), weatherResponse.getWind().getDeg())));
        weatherItems.add(new WeatherItem(Main.class, Main.getPressureInstance(weatherResponse.getMain().getPressure())));
        weatherItems.add(new WeatherItem(Clouds.class, new Clouds(weatherResponse.getClouds().getAll())));
        weatherItems.add(new WeatherItem(Main.class, Main.getHumidityInstance(weatherResponse.getMain().getHumidity())));
        weatherItems.add(new WeatherItem(Main.class, Main.getMaxTempInstance(weatherResponse.getMain().getTemp_max())));
        weatherItems.add(new WeatherItem(Main.class, Main.getMinTempInstance(weatherResponse.getMain().getTemp_min())));

        Adapter adapter = new Adapter(weatherItems, this, Adapter.WEATHER_INFO_ADAPTER);

        infoList.setAdapter(adapter);
        updateUI(cityName, weatherResponse.getName());
        updateUI(temp, String.valueOf((int) weatherResponse.getMain().getTemp()).concat(getResources().getString(R.string.celicus_text)));
        updateUI(weatherState, getResources().getString(R.string.desc_label_text)
                + " " + weatherResponse.getWeather().get(0).getDescription());

    }

    private void updateUI(View view, String data) {
        new Thread(() ->
                view.post(() ->
                        ((TextView) view).setText(data))).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // close realm instance to prevent crashes
        RealmCreator.closeRealm();
    }

    @Override
    public void onBackPressed() {
        finishAfterTransition();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
