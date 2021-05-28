package com.example.remoteds;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.database.model.WeatherResponse;
import com.example.localds.LocalDataSource;
import com.example.model.City;
import com.example.network.RetrofitClient;
import com.example.viewmodel.DataSourceController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RemoteDataSource {

    private static volatile RemoteDataSource remoteDataSource;
    private DataSourceController.RemoteController remoteController;

    private static final String TAG = "RemoteDataSource";

    private RemoteDataSource(@NonNull DataSourceController.RemoteController remoteController) {
        this.remoteController = remoteController;
    }

    public static RemoteDataSource getInstance(@NonNull DataSourceController.RemoteController remoteController) {
        if (remoteDataSource == null) {
            synchronized (RemoteDataSource.class) {
                if (remoteDataSource == null) {
                    remoteDataSource = new RemoteDataSource(remoteController);
                }
            }
        }

        return remoteDataSource;
    }

    public Disposable loadAndSaveWeatherInfo() {
        WeatherService client = RetrofitClient.INSTANCE
                .retrofitClient
                .createService(WeatherService.class);

        List<Observable<WeatherResponse>> citiesInfoOps = getCitiesWeather(client);

        Log.e(TAG, "loadAndSaveWeatherInfo: fetching weather cast info...");
        return Observable.merge(citiesInfoOps)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(weatherResponses -> {
                            LocalDataSource localDataSource = LocalDataSource.getInstance(new DataSourceController.LocalController() {
                                @Override
                                public void onMultipleWeatherInserted() {
                                    remoteController.onCitiesWeatherFetched(weatherResponses);
                                    Log.e(TAG, "onMultipleWeatherInserted: ");
                                }

                                @Override
                                public void onLocalSourceError(Throwable throwable) {
                                    Log.e(TAG, "onLocalSourceError: ", throwable);
                                }
                            });
                            localDataSource.insertOrUpdate(weatherResponses);

                        }
                        , throwable -> remoteController.onRemoteSourceError(throwable));
    }

    public static List<Observable<WeatherResponse>> getCitiesWeather(WeatherService client) {
        final Map<String, float[]> coord = City.Constants.coordinates;

        return new ArrayList<>(
                Arrays.asList(client.currentWeatherData(coord.get(City.Constants.AMMAN)[0], coord.get(City.Constants.AMMAN)[1], City.Constants.AMMAN_ID, RetrofitClient.API_KEY)
                        , client.currentWeatherData(coord.get(City.Constants.IRBID)[0], coord.get(City.Constants.IRBID)[1], City.Constants.IRBID_ID, RetrofitClient.API_KEY)
                        , client.currentWeatherData(coord.get(City.Constants.AQABA)[0], coord.get(City.Constants.AQABA)[1], City.Constants.AQABA_ID, RetrofitClient.API_KEY))
        );
    }
}
