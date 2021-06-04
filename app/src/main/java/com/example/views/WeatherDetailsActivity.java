package com.example.views;

import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.example.joweather.R;

public class WeatherDetailsActivity extends AppCompatActivity {

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



    }
}
