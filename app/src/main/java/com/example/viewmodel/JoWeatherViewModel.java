package com.example.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.database.model.RealmWeather;
import com.example.database.model.WeatherResponse;
import com.example.repo.WeatherRepository;

import java.util.ArrayList;
import java.util.List;

import app.WeatherApp;
import io.reactivex.disposables.CompositeDisposable;

public class JoWeatherViewModel extends AndroidViewModel
        implements DataSourceController {

    private final CompositeDisposable subscribers = new CompositeDisposable();

    private WeatherRepository repository;

    private static volatile JoWeatherViewModel INSTANCE;

    private static final String TAG = "JoWeatherViewModel";

    public JoWeatherViewModel(@NonNull WeatherApp application) {
        super(application);
        repository = WeatherRepository.getInstance(this);
    }

    public static JoWeatherViewModel getInstance(WeatherApp app) {
        if (INSTANCE == null) {
            synchronized (JoWeatherViewModel.class) {
                if (INSTANCE == null) {
                    INSTANCE = new JoWeatherViewModel(app);
                }
            }
        }
        return INSTANCE;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        subscribers.dispose();
    }

    public void loadAndSaveWeatherInfo() {
        Log.e(TAG, "loadAndSaveWeatherInfo: fetching weather cast info...");
        subscribers.add(repository.loadAndSaveWeatherInfo());
    }

    @Override
    public void onLocalSourceError(Throwable throwable) {
        Log.e(TAG, "onLocalSourceError: ", throwable);
    }

    @Override
    public void onRemoteSourceError(Throwable throwable) {
        Log.e(TAG, "onRemoteSourceError: ", throwable);
    }

    @Override
    public void onMultipleWeatherInserted() {
        Log.e(TAG, "onWeatherInserted: ");
    }


    @Override // remote data source
    public void onCitiesWeatherFetched(List<WeatherResponse> weatherResponses) {
        Log.e(TAG, "onCitiesWeatherFetched: " + weatherResponses.toString());
    }

}
