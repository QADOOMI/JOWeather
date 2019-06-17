package callbacks;

import weather.structure.WeatherResponse;

public interface CallBack {
    void sendWeatherData(WeatherResponse weatherResponse);

}
