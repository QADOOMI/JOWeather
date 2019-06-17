package mainpage.view.presenter;

import java.util.ArrayList;

import recyclerview.City;
import weather.view.presenter.IContacter;

public class MainActivityPresenter implements IContacter.IPresenter{

    // unit for handling data of activity
    private IContacter.IModel model;
    // unit for referencing the UI updates
    private IContacter.IMainView view;

    public MainActivityPresenter(IContacter.IMainView view) {
        this.view = view;
        model = new MainActivityModel();
    }

    /**
     * @implSpec getting the data from the Model unit(MainActivityModel) and send it to View unit(MainActivity)
     */
    @Override
    public void onWeatherDataLoaded() {
        ArrayList<City> cities = model.loadWeatherData(null);

        view.setViewContent(cities);
    }

}
