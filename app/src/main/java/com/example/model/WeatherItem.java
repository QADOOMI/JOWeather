package com.example.model;


import com.example.database.model.GeneralWeather;

public class WeatherItem {

    // type of weather data to show
    private Class<?> infoType;
    // for referencing the weather data
    private GeneralWeather weatherInfo;

    public WeatherItem() {
    }

    public WeatherItem(Class<?> infoType, GeneralWeather weatherInfo) {
        this.infoType = infoType;
        this.weatherInfo = weatherInfo;
    }

    public Class<?> getInfoType() {
        return infoType;
    }

    public void setInfoType(Class<?> infoType) {
        this.infoType = infoType;
    }

    public GeneralWeather getWeatherInfo() {
        return weatherInfo;
    }

    public void setWeatherInfo(GeneralWeather weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

}
