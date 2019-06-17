package retrofit.handling;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import weather.structure.WeatherResponse;


public interface WeatherService {

    /**
     * @param lat represent the latitude of city
     * @param lon represent the longitude of city
     * @param id represent the city id
     * @param appId represent my api subscription id
     * @GET request type and the continuation of API URL
     * @Query represent the data to sent
     * */
    @GET("weather?/")
    Observable<WeatherResponse> currentWeatherData(@Query("lat") float lat, @Query("lon") float lon
            , @Query("id") String id, @Query("APPID") String appId);

}