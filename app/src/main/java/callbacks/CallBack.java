package callbacks;

import com.example.database.model.WeatherResponse;

public interface CallBack {
    void sendWeatherData(WeatherResponse weatherResponse);

}
