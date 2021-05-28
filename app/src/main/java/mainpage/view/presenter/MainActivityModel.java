package mainpage.view.presenter;

import com.example.joweather.R;

import java.util.ArrayList;

import callbacks.CallBack;
import com.example.model.City;
import weather.view.presenter.IContacter;

public class MainActivityModel implements IContacter.IModel{
    public MainActivityModel() {
    }

    /**
     * @param weatherDataSender callback method for sending data to presenter
     *
     * @implNote getting the data from project files and City class
     * */
    @Override
    public ArrayList<City> loadWeatherData(CallBack weatherDataSender) {
        ArrayList<City> cities = new ArrayList<>();

        cities.add(new City(City.Constants.AMMAN, R.drawable.amman));
        cities.add(new City(City.Constants.IRBID, R.drawable.irbid));
        cities.add(new City(City.Constants.AQABA, R.drawable.aqaba));

        return cities;

    }

}
