package weather.structure;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherResponse {

    // for city coordination
    @SerializedName("coord")
    private Coord coord;

    // for number of clouds
    @SerializedName("clouds")
    private Clouds clouds;

    // for sunrise and sunset data
    @SerializedName("sys")
    private Sys sys;

    // list contains weather description, id of weather loaded, main description of weather
    @SerializedName("weather")
    private ArrayList<Weather> weather;

    // contains temp, min temp, max temp, pressure and humidity
    @SerializedName("main")
    private Main main;

    // contains wind speed and wind degree
    @SerializedName("wind")
    private Wind wind;

    @SerializedName("dt")
    private double dt;

    // city id
    @SerializedName("id")
    private int id;

    // city name
    @SerializedName("name")
    private String name;

    //
    @SerializedName("cod")
    private double cod;

    // for error messaging
    private String mssage;

    public WeatherResponse() {
    }

    public WeatherResponse(Coord coord, Sys sys, ArrayList<Weather> weather
            , Main main, Wind wind
            , double dt, int id, String name, double cod) {
        this.coord = coord;
        this.sys = sys;
        this.weather = weather;
        this.main = main;
        this.wind = wind;
        this.dt = dt;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Coord getCoord() {
        return coord;
    }

    public Sys getSys() {
        return sys;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public double getDt() {
        return dt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCod() {
        return cod;
    }


    // for debuging purpose
    @Override
    public String toString() {
        return "WeatherResponse{" +
                "coord=" + coord.toString() +
                ", clouds=" + clouds.toString() +
                ", sys=" + sys.toString() +
                ", weather=" + weather.toString() +
                ", main=" + main.toString() +
                ", wind=" + wind.toString() +
                ", dt=" + dt +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", cod=" + cod +
                '}';
    }
}