package weather.view.presenter;

import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import callbacks.CallBack;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import recyclerview.City;
import retrofit.handling.RetrofitClient;
import retrofit.handling.WeatherService;
import weather.realm.RealmCreator;
import weather.structure.IRealmWeatherBuilder;
import weather.structure.RealmWeather;

public class WeatherModel extends AsyncTask<CallBack, Void, Void> implements IContacter.IModel{

    // for debug purposes
    private static final String TAG = WeatherModel.class.getSimpleName();
    // the api key for connecting to my API subscription
    private final static String API_KEY = "02f3efe3cdf397ad6b96ad5b64d273d8";
    /*
     * coord sorted in the array with latitude then longtitude
     * store the city coordinates
     * */
    private Float[] coords;
    // city id for loading specific city weather
    private int cityId;

    private String requestedCity;


    public WeatherModel(String requestedCity) {
        this.requestedCity = requestedCity;
    }


    /**
     * @param weatherDataSender is callback function to send the loaded data to presenter
     * @implSpec load the data from API using retrofit for handling http requests,
     *           RxJava for handling data in JSON file type
     *           storing the loaded data into realm instance
     * @implNote the weather data instance created using builder design pattern
     *
     * @apiNote the date of weather data loaded written to the Realm with consideration of api level
     * */
    @Override
    public ArrayList<City> loadWeatherData(CallBack weatherDataSender) {

        this.execute(weatherDataSender);

        return null;
    }


    @Override
    protected void onPreExecute() throws IllegalStateException {
        switch (requestedCity) {
            case "Amman":
                coords = City.CitiesConstants.citiesCoord.get(City.CitiesConstants.AMMAN);
                cityId = City.CitiesConstants.AMMAN_ID;
                return;
            case "Irbid":
                coords = City.CitiesConstants.citiesCoord.get(City.CitiesConstants.IRBID);
                cityId = City.CitiesConstants.IRBID_ID;
                return;
            case "Aqaba":
                coords = City.CitiesConstants.citiesCoord.get(City.CitiesConstants.AQABA);
                cityId = City.CitiesConstants.AQABA_ID;
                return;
            default:
                String message = "No such city available yet.";
        }
    }

    @Override
    protected Void doInBackground(CallBack... weatherDataSender) {
        if (coords == null) {
            return null;
        }
        WeatherService client = RetrofitClient.getRetrofit().create(WeatherService.class);

        client.currentWeatherData(coords[0], coords[1], String.valueOf(cityId), API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.from(Looper.getMainLooper()))
                .subscribe(weatherData -> {
                            Log.e(TAG, "doInBackground: \n" + weatherData);

                            weatherDataSender[0].sendWeatherData(weatherData);
                            String nowDate = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                nowDate = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ofPattern("yyyy/MM/dd")).toString();
                            } else {
                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                                Date date = new Date();
                                nowDate = dateFormat.format(date);

                            }

                            RealmWeather realmWeatherData = new IRealmWeatherBuilder.RealmWeatherBuilder()
                                    .withId(weatherData.getId())
                                    .withDescription(weatherData.getWeather().get(0).getDescription())
                                    .withWindSpeed(weatherData.getWind().getSpeed())
                                    .withWindDeg(weatherData.getWind().getDeg())
                                    .withCloud(String.valueOf(weatherData.getClouds().getAll()))
                                    .withTemp(weatherData.getMain().getTemp())
                                    .withPressure(String.valueOf(weatherData.getMain().getPressure()))
                                    .withMinTemp(weatherData.getMain().getTemp_min())
                                    .withMaxTemp(weatherData.getMain().getTemp_max())
                                    .withWeatherDate(nowDate)
                                    .withCityName(weatherData.getName())
                                    .withHumidty(weatherData.getMain().getHumidity())
                                    .withLongtitude(weatherData.getCoord().getLon())
                                    .withLatitude(weatherData.getCoord().getLat())
                                    .buildWeather();


                            Realm realmDatabase = RealmCreator.newInstance();

                            try {
                                realmDatabase.executeTransactionAsync((Realm realm) -> {
                                    realm.insertOrUpdate(realmWeatherData);
                                    Log.e(TAG, "doInBackground: inserted successfully");
                                });
                            } catch (Exception e) {
                                Log.e(TAG, "loadWeatherData: ", e);
                                e.printStackTrace();
                            }

                        }, (throwable) -> android.util.Log.e(TAG, "getWeatherData: ", throwable)
                        , () -> Log.d(TAG, "onComplete."));
        coords = null;

        return null;
    }
}
