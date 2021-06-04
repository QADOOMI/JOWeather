package com.example.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.database.model.RealmWeather;
import com.example.database.model.WeatherResponse;
import com.example.model.WeatherError;
import com.example.repo.WeatherRepository;

import java.util.ArrayList;
import java.util.List;

import app.WeatherApp;
import io.reactivex.disposables.CompositeDisposable;

public class JoWeatherViewModel extends AndroidViewModel
        implements DataSourceController {

    private final CompositeDisposable subscribers = new CompositeDisposable();

    private WeatherRepository repository;

    private MutableLiveData<List<RealmWeather>> weathers = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<WeatherError> errorHappen = new MutableLiveData<>();

    private static volatile JoWeatherViewModel INSTANCE;

    private static final String TAG = "JoWeatherViewModel";

    public JoWeatherViewModel(@NonNull WeatherApp application) {
        super(application);
        repository = new WeatherRepository(this);
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
        errorHappen.postValue(new WeatherError(throwable.getMessage(), true));
    }

    @Override
    public void onRemoteSourceError(Throwable throwable) {
        Log.e(TAG, "onRemoteSourceError: ", throwable);
        errorHappen.postValue(new WeatherError(throwable.getMessage(), false));
    }

    @Override
    public void onMultipleWeatherInserted(List<RealmWeather> weathers) {
        Log.e(TAG, "onWeatherInserted: " + weathers.size());
        if (weathers != null && weathers.size() > 0) {
            this.weathers.postValue(weathers);
        }
    }

    @Override
    public void newWeatherInfoInserted(RealmWeather realmModel) {
        Log.e(MainActivityViewModel.class.getSimpleName(), "newWeatherInfoInserted: " + realmModel.toString());
        if (weathers.getValue().size() > 0) {
            int i = 0;
            for (RealmWeather weather : weathers.getValue()) {
                if (realmModel.getId() == weather.getId()) {
                    weathers.getValue().set(i, realmModel);
                    break;
                }
                i++;
            }
        } else {
            weathers.getValue().add(realmModel);
        }

        weathers.postValue(weathers.getValue());
    }

    public MutableLiveData<List<RealmWeather>> getWeathers() {
        return weathers;
    }

    public MutableLiveData<WeatherError> getErrorHappen() {
        return errorHappen;
    }

    @Override // remote data source
    public void onCitiesWeatherFetched(List<WeatherResponse> weatherResponses) {
        Log.e(TAG, "onCitiesWeatherFetched: " + weatherResponses.toString());
    }

}
