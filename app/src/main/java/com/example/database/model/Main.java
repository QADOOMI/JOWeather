package com.example.database.model;


import com.google.gson.annotations.SerializedName;

public class Main implements GeneralWeather {

    private static final String TAG = Main.class.getSimpleName();
    public static final double KELVIN = 273.15;

    @SerializedName("temp")
    private double temp;

    @SerializedName("humidity")
    private double humidity;

    @SerializedName("pressure")
    private double pressure;

    @SerializedName("temp_min")
    private double temp_min;

    @SerializedName("temp_max")
    private double temp_max;

    public Main(double temp, double humidity, double pressure, double temp_min, double temp_max) {
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }

    public static Main getMinTempInstance(double temp_min) {
        return new Main(0, 0, 0, temp_min, 0);
    }

    public static Main getMaxTempInstance(double temp_max) {
        return new Main(0, 0, 0, 0, temp_max);
    }

    public static Main getPressureInstance(double pressure) {
        return new Main(0, 0, pressure, 0, 0);
    }

    public static Main getHumidityInstance(double humidity) {
        return new Main(0, humidity, 0, 0, 0);
    }

    public double getTemp() {
        return temp - KELVIN;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }


    @Override
    public String toString() {
        return "Main{" +
                "temp=" + temp +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", temp_min=" + temp_min +
                ", temp_max=" + temp_max +
                '}';
    }
}