package weather.structure;

public interface IRealmWeatherBuilder {

    RealmWeatherBuilder withId(int id);

    RealmWeatherBuilder withDescription(String description);

    RealmWeatherBuilder withWindSpeed(double speed);

    RealmWeatherBuilder withWindDeg(double deg);

    RealmWeatherBuilder withCloud(String cloud);

    RealmWeatherBuilder withTemp(double temp);

    RealmWeatherBuilder withPressure(String pressure);

    RealmWeatherBuilder withHumidty(double humidty);

    RealmWeatherBuilder withMinTemp(double minTemp);

    RealmWeatherBuilder withMaxTemp(double maxTemp);

    RealmWeatherBuilder withCityName(String cityName);

    RealmWeatherBuilder withWeatherDate(String weatherDate);

    RealmWeatherBuilder withLatitude(double lat);

    RealmWeatherBuilder withLongtitude(double lon);

    RealmWeather buildWeather();


     public final class RealmWeatherBuilder implements IRealmWeatherBuilder {

        private RealmWeather realmWeather;

        public RealmWeatherBuilder(){
            realmWeather = new RealmWeather();
        }

        @Override
        public RealmWeatherBuilder withId(int id) {
            realmWeather.setId(id);
            return this;
        }

        @Override
        public RealmWeatherBuilder withDescription(String description) {
             realmWeather.setDescription(description);
            return this;
        }

        @Override
        public RealmWeatherBuilder withWindSpeed(double speed) {
             realmWeather.setSpeed(speed);
            return this;
        }

        @Override
        public RealmWeatherBuilder withWindDeg(double deg) {
             realmWeather.setDeg(deg);
            return this;
        }

        @Override
        public RealmWeatherBuilder withCloud(String cloud) {
             realmWeather.setCloud(cloud);
            return this;
        }

        @Override
        public RealmWeatherBuilder withTemp(double temp) {
             realmWeather.setTemp(temp);
            return this;
        }

        @Override
        public RealmWeatherBuilder withPressure(String pressure) {
             realmWeather.setPressure(pressure);
            return this;
        }

        @Override
        public RealmWeatherBuilder withHumidty(double humidty) {
             realmWeather.setHumidty(humidty);
            return this;
        }

        @Override
        public RealmWeatherBuilder withMinTemp(double minTemp) {
             realmWeather.setMinTemp(minTemp);
            return this;
        }

        @Override
        public RealmWeatherBuilder withMaxTemp(double maxTemp) {
             realmWeather.setMaxTemp(maxTemp);
            return this;
        }

        @Override
        public RealmWeatherBuilder withCityName(String cityName) {
             realmWeather.setCityName(cityName);
            return this;
        }

        @Override
        public RealmWeatherBuilder withWeatherDate(String weatherDate) {
             realmWeather.setWeatherDate(weatherDate);
            return this;
        }

        @Override
        public RealmWeatherBuilder withLatitude(double lat) {
             realmWeather.setLat(lat);
            return this;
        }

        @Override
        public RealmWeatherBuilder withLongtitude(double lon) {
             realmWeather.setLon(lon);
            return this;
        }

        @Override
        public RealmWeather buildWeather() {
            return realmWeather;
        }
    }
}