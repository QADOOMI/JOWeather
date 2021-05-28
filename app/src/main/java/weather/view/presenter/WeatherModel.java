package weather.view.presenter;

import java.util.ArrayList;

import callbacks.CallBack;

import com.example.model.City;

public class WeatherModel /*extends AsyncTask<CallBack, Void, Void> */implements IContacter.IModel{

    // for debug purposes
    private static final String TAG = WeatherModel.class.getSimpleName();
    // the api key for connecting to my API subscription
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

       // this.execute(weatherDataSender);

        return null;
    }


 /*   @Override
    protected void onPreExecute() throws IllegalStateException {

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
                            long nowDate = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                nowDate = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ofPattern("yyyy/MM/dd")).toString();
                            } else {
                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                                Date date = new Date();
                                nowDate = dateFormat.format(date);

                            }

                            RealmWeather realmWeatherData = new IRealmWeatherBuilder.RealmWeatherBuilder(weatherData)
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
    }*/
}
