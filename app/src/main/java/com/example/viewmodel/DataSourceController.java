package com.example.viewmodel;

import com.example.database.model.RealmWeather;
import com.example.database.model.WeatherResponse;

import java.util.List;

public interface DataSourceController {

    default void onLocalSourceError(Throwable throwable) {

    }

    default void onMultipleWeatherInserted(List<RealmWeather> weathers) {

    }

    default void newWeatherInfoInserted(RealmWeather realmModel) {

    }

    default void weatherInfoFetched(RealmWeather realmWeather) {

    }

    default void onRemoteSourceError(Throwable throwable) {

    }

    default void onCitiesWeatherFetched(List<WeatherResponse> weatherResponses) {

    }

    default void noNewDataAvailable() {

    }
}
