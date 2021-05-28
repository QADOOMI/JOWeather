package com.example.viewmodel;

import com.example.database.model.RealmWeather;
import com.example.database.model.WeatherResponse;

import java.util.List;

public interface DataSourceController {

    interface LocalController {
        default void onLocalSourceError(Throwable throwable) {

        }

        default void onMultipleWeatherInserted() {

        }

        default void newWeatherInfoInserted(RealmWeather realmModel) {

        }

        default void weatherInfoFetched(RealmWeather realmWeather) {

        }
    }

    interface RemoteController {
        void onRemoteSourceError(Throwable throwable);

        void onCitiesWeatherFetched(List<WeatherResponse> weatherResponses);
    }
}
