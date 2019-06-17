package weather.structure;

import io.realm.RealmObject;
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

    // main
    private double temp;
    @Required
    private String pressure;
    private double humidty;
    private double minTemp;
    private double maxTemp;

    @Required
    private String cityName;

    // date of weather taken
    @Required
    private String weatherDate;

    // coordination
    private double lat;
    private double lon;

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

    public String getWeatherDate() {
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
        this.pressure = pressure;
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
    }

    public void setWeatherDate(String weatherDate) {
        this.weatherDate = weatherDate;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
