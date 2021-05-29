package com.example.database.model;

import android.util.Log;

import androidx.annotation.DrawableRes;

import com.example.joweather.R;

import app.WeatherApp;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class RealmWeather extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String description;

    // wind
    private double speed;
    private double deg;

    // cloud
    @Required
    private String cloud;

    // main temp
    private double temp;
    @Required
    private String pressure;
    private double humidty;
    private double minTemp;
    private double maxTemp;

    @Required
    private String cityName;

    // date of weather taken
    private long weatherDate;

    // coordination
    private double lat;
    private double lon;

    @Ignore
    private int cityImage;

    public RealmWeather() {
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDeg() {
        return deg;
    }

    public String getCloud() {
        return cloud;
    }

    public String getPressure() {
        return pressure;
    }

    public double getHumidty() {
        return humidty;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public String getCityName() {
        return cityName;
    }

    public long getWeatherDate() {
        return weatherDate;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public double getTemp() {
        return temp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDeg(double deg) {
        this.deg = deg;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure + " P";
    }

    public void setHumidty(double humidty) {
        this.humidty = humidty;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
        if (cityName.contains("Aqaba")) {
            cityImage = R.drawable.aqaba;

        } else if (cityName.contains("Amman")) {
            cityImage = R.drawable.amman;

        } else if (cityName.contains("Irbid")) {
            cityImage = R.drawable.irbid;

        }
        Log.e(RealmWeather.class.getSimpleName(), "setCityName: " + cityImage);
    }

    public void setWeatherDate(long weatherDate) {
        this.weatherDate = weatherDate;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public boolean isEmpty() {
        return cloud == null && humidty == 0.0 && temp == 0.0;
    }

    public void updateWithNewValues(RealmWeather realmModel) {
        this.lon = realmModel.getLon();
        this.lat = realmModel.getLat();
        this.temp = realmModel.getTemp();
        this.humidty = realmModel.getHumidty();
        this.cityName = realmModel.getCityName();
        this.cloud = realmModel.getCloud();
        this.deg = realmModel.getDeg();
        this.description = realmModel.getDescription();
        this.maxTemp = realmModel.getMaxTemp();
        this.minTemp = realmModel.getMinTemp();
        this.pressure = realmModel.getPressure();
        this.speed = realmModel.getSpeed();
        this.weatherDate = realmModel.getWeatherDate();
    }

    @Override
    public String toString() {
        return "RealmWeather{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", cityName='" + cityName + '\'' +
                ", weatherDate=" + weatherDate +
                '}' + '\n';
    }

    @DrawableRes
    public int getCityImage() {
        return cityImage;
    }
}
