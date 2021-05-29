package com.example.repo;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.database.model.IRealmWeatherBuilder;
import com.example.database.model.RealmWeather;
import com.example.database.model.WeatherResponse;
import com.example.database.realm.RealmCreator;
import com.example.date.CalendarUtils;
import com.example.model.City;
import com.example.network.RetrofitClient;
import com.example.viewmodel.DataSourceController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Case;

public final class WeatherRepository {

    private static volatile WeatherRepository repository;

    private DataSourceController sourceController;
    private RealmCreator realmCreator;
    private WeatherService weatherService;

    private static final String TAG = "WeatherRepository";

    private WeatherRepository(DataSourceController controller) {
        this.sourceController = controller;

        realmCreator = RealmCreator.newInstance();
        weatherService = RetrofitClient.INSTANCE
                .retrofitClient
                .createService(WeatherService.class);
    }

    public static WeatherRepository getInstance(@NonNull DataSourceController remoteController) {
        if (repository == null) {
            synchronized (WeatherRepository.class) {
                if (repository == null) {
                    repository = new WeatherRepository(remoteController);
                }
            }
        }

        return repository;
    }

    public void insertOrUpdate(@NonNull WeatherResponse weatherResponse) {
        Log.e(TAG, "loadWeatherInfo finished = " + weatherResponse.toString());
        Log.e(TAG, "loadAndSaveWeatherInfo: inserting weather...");

        realmCreator.executeAsync(realm ->
                        realm.insertOrUpdate(new IRealmWeatherBuilder.RealmWeatherBuilder(weatherResponse)
                                .build())
                , () -> {
                    Log.e(TAG, "loadAndSaveWeatherInfo: SUCCESS");
                    sourceController.onMultipleWeatherInserted();
                },
                error -> {
                    Log.e(TAG, "loadAndSaveWeatherInfo: Error, ", error);
                    sourceController.onLocalSourceError(error);
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

        Log.e(TAG, "insertOrUpdate: " + weathers.toString());

        realmCreator.executeAsync(realm -> realm.insertOrUpdate(weathers)
                , () -> {
                    Log.e(TAG, "loadAndSaveWeatherInfo: SUCCESS");
                    sourceController.onMultipleWeatherInserted();
                },
                error -> {
                    Log.e(TAG, "loadAndSaveWeatherInfo: Error, ", error);
                    sourceController.onLocalSourceError(error);
                });
    }

    public ArrayList<RealmWeather> fetchWeatherInfo() {
        return new ArrayList<>(Arrays.asList(
                handleCityInfo("Amman"),

                handleCityInfo("Irbid"),

                handleCityInfo("Aqaba")
        ));
    }

    private RealmWeather handleCityInfo(String city) {
        Calendar calendar = CalendarUtils.getZeroTimeCalendar();

        Object realmObj = realmCreator.where(RealmWeather.class)
                .equalTo("weatherDate", calendar.getTimeInMillis())
                .and()
                .contains("cityName", city, Case.INSENSITIVE)
                .findFirst();

        RealmWeather realmWeather = null;
        if (realmObj instanceof RealmWeather)
            realmWeather = ((RealmWeather) realmObj);

        if (realmWeather != null) {
            Log.e(TAG, "firstAsFlowable data size: " + realmWeather.toString());
            realmWeather.addChangeListener((realmWeathers1, changeSet) -> {
                Log.e(TAG, "firstAsFlowable, isDeleted: " + changeSet.isDeleted());
                if (changeSet.getChangedFields().length > 0)
                    sourceController.newWeatherInfoInserted(((RealmWeather) realmWeathers1));
            });

        } else
            Log.e(TAG, "firstAsFlowable: weather is null");

        return realmWeather;
    }

//    private RealmWeather getRealmWeatherFrom(RealmResults<RealmWeather> realmWeather) {
//        return LiveDataReactiveStreams.fromPublisher(realmWeather.asFlowable()
//                .filter(RealmResults::isLoaded)
//                .map(realmWeathers2 -> {
//                    Log.e(TAG, "handleCityInfo: " + realmWeathers2.toString());
//                    return realmWeathers2;
//                })
//                .flatMapIterable((Function<RealmResults<RealmWeather>, Iterable<RealmWeather>>) realmWeathers2 ->
//                        realmWeathers2.subList(0, realmWeathers2.size() - 1))
//        ).getValue();
//    }

    public Disposable loadAndSaveWeatherInfo() {
        List<Observable<WeatherResponse>> citiesInfoOps = getCitiesWeather(weatherService);

        Log.e(TAG, "loadAndSaveWeatherInfo: fetching weather cast info...");
        return Observable.merge(citiesInfoOps)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(this::insertOrUpdate
                        , throwable -> sourceController.onRemoteSourceError(throwable));
    }

    public static List<Observable<WeatherResponse>> getCitiesWeather(WeatherService client) {
        final Map<String, float[]> coord = City.Constants.coordinates;

        return new ArrayList<>(
                Arrays.asList(client.currentWeatherData(coord.get(City.Constants.AMMAN)[0],
                        coord.get(City.Constants.AMMAN)[1],
                        City.Constants.AMMAN_ID,
                        RetrofitClient.API_KEY)
                        , client.currentWeatherData(coord.get(City.Constants.IRBID)[0],
                                coord.get(City.Constants.IRBID)[1],
                                City.Constants.IRBID_ID,
                                RetrofitClient.API_KEY)
                        , client.currentWeatherData(coord.get(City.Constants.AQABA)[0],
                                coord.get(City.Constants.AQABA)[1],
                                City.Constants.AQABA_ID,
                                RetrofitClient.API_KEY))
        );
    }
}
