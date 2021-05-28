package com.example.localds;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.database.model.IRealmWeatherBuilder;
import com.example.database.model.RealmWeather;
import com.example.database.model.WeatherResponse;
import com.example.database.realm.RealmCreator;
import com.example.date.CalendarUtils;
import com.example.viewmodel.DataSourceController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmObjectChangeListener;
import io.realm.RealmQuery;

public class LocalDataSource {

    private static volatile LocalDataSource remoteDataSource;

    private Realm realm = RealmCreator.newInstance();

    private DataSourceController.LocalController localController;

    private static final String TAG = "LocalDataSource";

    private LocalDataSource(DataSourceController.LocalController localController) {
        this.localController = localController;
    }

    public static LocalDataSource getInstance(@NonNull DataSourceController.LocalController localController) {
        if (remoteDataSource == null) {
            synchronized (LocalDataSource.class) {
                if (remoteDataSource == null) {
                    remoteDataSource = new LocalDataSource(localController);
                }
            }
        }

        return remoteDataSource;
    }

    public void insertOrUpdate(@NonNull WeatherResponse weatherResponse) {
        Log.e(TAG, "loadWeatherInfo finished = " + weatherResponse.toString());
        Log.e(TAG, "loadAndSaveWeatherInfo: inserting weather...");

        realm.executeTransactionAsync(realm ->
                        realm.insertOrUpdate(new IRealmWeatherBuilder.RealmWeatherBuilder(weatherResponse)
                                .build())
                , () -> {
                    Log.e(TAG, "loadAndSaveWeatherInfo: SUCCESS");
                    localController.onMultipleWeatherInserted();
                },
                error -> {
                    Log.e(TAG, "loadAndSaveWeatherInfo: Error, ", error);
                    localController.onLocalSourceError(error);
                });
    }

    public void insertOrUpdate(List<WeatherResponse> weatherResponses) {
        Log.e(TAG, "loadWeatherInfo finished = " + weatherResponses.toString());
        Log.e(TAG, "loadAndSaveWeatherInfo: inserting multiple weather instances...");

        List<RealmWeather> weathers = new ArrayList<>();
        for (WeatherResponse weatherResponse : weatherResponses) {
            weathers.add(new IRealmWeatherBuilder.RealmWeatherBuilder(weatherResponse)
                    .build());
        }

        realm.executeTransactionAsync(realm -> realm.insertOrUpdate(weathers)
                , () -> {
                    Log.e(TAG, "loadAndSaveWeatherInfo: SUCCESS");
                    localController.onMultipleWeatherInserted();
                },
                error -> {
                    Log.e(TAG, "loadAndSaveWeatherInfo: Error, ", error);
                    localController.onLocalSourceError(error);
                });
    }

    public void fetchWeatherInfo() {
        Calendar calendar = CalendarUtils.getZeroTimeCalendar();

        RealmQuery<RealmWeather> realmWeathers = realm.where(RealmWeather.class)
                .sort("weatherDate")
                .limit(1)
                .equalTo("weatherDate", calendar.getTimeInMillis());

        firstAsFlowable(realmWeathers, "amman");
        firstAsFlowable(realmWeathers, "irbid");
        firstAsFlowable(realmWeathers, "aqaba");
    }

    private void firstAsFlowable(RealmQuery<RealmWeather> realmWeathers, String city) {
        RealmWeather realmWeather = realmWeathers.equalTo("cityName", city, Case.INSENSITIVE)
                .findFirstAsync();

        if (realmWeather != null) {
            Log.e(TAG, "firstAsFlowable: " + realmWeather.toString());
            realmWeather.addChangeListener((RealmObjectChangeListener<RealmWeather>) (realmModel, changeSet) -> {
                Log.e(TAG, "firstAsFlowable: " + changeSet.toString());
                if (!changeSet.isDeleted()) {
                    boolean changed;
                    for (String fieldName : changeSet.getChangedFields()) {
                        changed = changeSet.isFieldChanged(fieldName);
                        if (changed) {
                            localController.newWeatherInfoInserted(realmModel);
                            return;
                        }
                    }
                }
            });

            localController.weatherInfoFetched(realmWeather);


        } else
            Log.e(TAG, "firstAsFlowable: weather is null");

    }
}
