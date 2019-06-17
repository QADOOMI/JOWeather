package weather.view.presenter;

public class WeatherPresenter implements IContacter.IPresenter{

    // view instance to connect to Activity
    private IContacter.IView view;
    // model instance for connecting the data handler unit
    private WeatherModel model;

    public WeatherPresenter(IContacter.IView view, final String requestedCity) {
        this.view = view;
        model = new WeatherModel(requestedCity);
    }

    /**
     * @implSpec get the data from Model unit and send it to UI(Activity)
     * */
    @Override
    public void onWeatherDataLoaded() {
        model.loadWeatherData(weatherResponse -> view.setViewContent(weatherResponse));
    }
}
