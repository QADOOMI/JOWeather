package weather.view.presenter;

import java.util.ArrayList;

import callbacks.CallBack;
import com.example.model.City;
import com.example.database.model.WeatherResponse;

public interface IContacter {

    // handling loading data and writing to database
    interface IModel {
        ArrayList<City> loadWeatherData(CallBack weatherDataSender);
    }

    // view handling
    interface IView {
        void setViewContent(WeatherResponse weatherResponse);
    }

    // logic and user input processing
    interface IPresenter {
        void onWeatherDataLoaded();
    }

    // main page view handling
    interface IMainView {
        void setViewContent(ArrayList<City> cities);
    }

}
