package com.example.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.database.model.RealmWeather;
import com.example.repo.WeatherRepository;

import java.util.ArrayList;

public final class MainActivityViewModel extends ViewModel
        implements DataSourceController {

    private final MutableLiveData<ArrayList<RealmWeather>> cityWeather = new MutableLiveData<ArrayList<RealmWeather>>() {{
        setValue(new ArrayList<>());
    }};

    private WeatherRepository repository;

    public MainActivityViewModel() {
        repository = WeatherRepository.getInstance(this);
    }

    public void fetchWeatherInfo() {
        Log.e(MainActivityViewModel.class.getSimpleName(), "loadAndSaveWeatherInfo: fetching weather cast info...");
        cityWeather.setValue(repository.fetchWeatherInfo());
    }

    public MutableLiveData<ArrayList<RealmWeather>> getCityWeather() {
        return cityWeather;
    }

    @Override
    public void onLocalSourceError(Throwable throwable) {

    }

    @Override
    public void newWeatherInfoInserted(RealmWeather realmModel) {
        Log.e(MainActivityViewModel.class.getSimpleName(), "newWeatherInfoInserted: " + realmModel.toString());
        for (RealmWeather weather : cityWeather.getValue())
            if (realmModel.getId() == weather.getId()) {
                weather.updateWithNewValues(realmModel);
                cityWeather.setValue(cityWeather.getValue());
            }
    }

    @Override // local data source
    public void weatherInfoFetched(RealmWeather realmWeather) {
        Log.e(MainActivityViewModel.class.getSimpleName(), "weatherInfoFetched: " + realmWeather.toString());
        cityWeather.getValue()
                .add(realmWeather);
        cityWeather.postValue(cityWeather.getValue());
    }

}
