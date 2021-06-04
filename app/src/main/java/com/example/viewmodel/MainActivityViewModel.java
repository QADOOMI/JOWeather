package com.example.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.database.model.RealmWeather;
import com.example.repo.WeatherRepository;

import java.util.ArrayList;
import java.util.List;

public final class MainActivityViewModel extends ViewModel
        implements DataSourceController {

    private final MutableLiveData<ArrayList<RealmWeather>> cityWeather = new MutableLiveData<ArrayList<RealmWeather>>() {{
        setValue(new ArrayList<>());
    }};

    private WeatherRepository repository;

    private static final String TAG = "MainActivityViewModel";

    public MainActivityViewModel() {
        Log.e(TAG, "MainActivityViewModel: creating repo");
        repository = new WeatherRepository(this);
    }

    public void fetchWeatherInfo() {
        Log.e(MainActivityViewModel.class.getSimpleName(), "loadAndSaveWeatherInfo: fetching weather cast info...");
        ArrayList<RealmWeather> weatherList = new ArrayList<>(repository.fetchWeatherInfo());

        ArrayList<RealmWeather> nonNullWeatherItems = new ArrayList<>();

        for (RealmWeather realmWeather : weatherList)
            if (realmWeather != null)
                nonNullWeatherItems.add(realmWeather);

        if (nonNullWeatherItems.size() > 0) {
            Log.d(MainActivityViewModel.class.getSimpleName(), "fetchWeatherInfo: weather items is full");
            cityWeather.setValue(nonNullWeatherItems);

        } else {
            Log.e(MainActivityViewModel.class.getSimpleName(), "fetchWeatherInfo: weather items is empty");

            repository.loadAndSaveWeatherInfo();

        }

    }

    public MutableLiveData<ArrayList<RealmWeather>> getCityWeather() {
        return cityWeather;
    }

    @Override
    public void onLocalSourceError(Throwable throwable) {

    }

    @Override
    public void noNewDataAvailable() {
        Log.e(MainActivityViewModel.class.getSimpleName(), "noNewDataAvailable: ");
        cityWeather.postValue(cityWeather.getValue());
    }

    @Override
    public void onMultipleWeatherInserted(List<RealmWeather> weathers) {
        cityWeather.postValue(new ArrayList<>(weathers));
    }

    @Override
    public void newWeatherInfoInserted(RealmWeather realmModel) {
        Log.e(MainActivityViewModel.class.getSimpleName(), "newWeatherInfoInserted: " + realmModel.toString());
        if (cityWeather.getValue().size() > 0) {
            for (RealmWeather weather : cityWeather.getValue())
                if (realmModel.getId() == weather.getId()) {
                    weather.updateWithNewValues(realmModel);
                }

        } else {
            cityWeather.getValue().add(realmModel);
        }

        cityWeather.postValue(cityWeather.getValue());
    }

    @Override // local data source
    public void weatherInfoFetched(RealmWeather realmWeather) {
        Log.e(MainActivityViewModel.class.getSimpleName(), "weatherInfoFetched: " + realmWeather.toString());
        cityWeather.getValue()
                .add(realmWeather);
        cityWeather.postValue(cityWeather.getValue());
    }

}
