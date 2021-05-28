package recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.model.Clouds;
import com.example.database.model.Main;
import com.example.database.model.Wind;
import com.example.joweather.R;
import com.example.model.City;
import com.example.model.WeatherItem;
import com.example.views.MainActivity;
import com.example.views.WeatherDetailsActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    // TAG for debug purposes
    private static final String TAG = Adapter.class.getSimpleName();
    // contains cities objects to represent it as views
    private List<City> cities;
    //  contains weather info objects to represent it as views
    private List<WeatherItem> weatherInfo;
    // id for viewing list of cities(MainActivity)
    public static final int CITY_ADAPTER = 55;
    // id for viewing list of weather data(WeatherDetailsActivity)
    public static final int WEATHER_INFO_ADAPTER = 54;
    // for storing layout based on the type of views
    private final int layout;
    // store type of layout to represent
    private final int layoutRequest;
    // store the context of the activity
    private Context context;

    public Adapter(List<? extends Object> data, Context context, int layoutRequest) {
        this.layoutRequest = layoutRequest;
        this.context = context;

        // determine the type of layuot
        if (isCityAdapter()) {
            this.cities = (ArrayList<City>) data;
            layout = R.layout.city_item;
        } else {
            layout = R.layout.weather_data_item;
            this.weatherInfo = (ArrayList<WeatherItem>) data;
        }
    }


    // attach the list layout view to the recyclerview
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);


        return new ViewHolder(view);
    }

    // for updating the UI with data in ArrayList based on the type
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isCityAdapter()) {
            City city = cities.get(position);
            animateItems(holder.cityCard);
            holder.image.post(() ->
                    holder.image.setImageDrawable(ContextCompat.getDrawable(context, city.getImage()))
            );

            holder.name.setText(city.getName());
            holder.cityCard.setOnClickListener((View view) -> {
                // determine the shared element to animate
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity) context
                        , Pair.create(holder.image, context.getResources().getString(R.string.city_image_transition_text)));

                Intent goToWeather = new Intent((MainActivity) context, WeatherDetailsActivity.class);
                goToWeather.putExtra(City.class.getSimpleName(), city.getImage());
                goToWeather.putExtra(City.Constants.REQUESTED_CITY, city.getName());

                context.startActivity(goToWeather, optionsCompat.toBundle());

            });
        } else {
            WeatherItem weatherItem = weatherInfo.get(position);
            animateItems(holder.weatherDataLayout);
            if (weatherItem.getInfoType() == Wind.class) {
                Wind wind = ((Wind) weatherItem.getWeatherInfo());
                updateUiBasedOnWeatherType(holder, context.getResources().getDrawable(R.drawable.windy, null)
                        , Wind.class.getSimpleName(), String.valueOf(wind.getSpeed())
                                .concat(context.getResources().getString(R.string.kelo_m_text)));

            } else if (weatherItem.getInfoType() == Clouds.class) {
                Clouds clouds = ((Clouds) weatherItem.getWeatherInfo());
                updateUiBasedOnWeatherType(holder, context.getResources().getDrawable(R.drawable.cloud, null)
                        , Clouds.class.getSimpleName(), String.valueOf(clouds.getAll()));

            } else if (weatherItem.getInfoType() == Main.class) {
                Main main = ((Main) weatherItem.getWeatherInfo());
                if (main.getPressure() != 0)
                    updateUiBasedOnWeatherType(holder, context.getResources().getDrawable(R.drawable.pressure, null)
                            , "Pressure", String.valueOf(main.getPressure()));
                else if (main.getHumidity() != 0)
                    updateUiBasedOnWeatherType(holder, context.getResources().getDrawable(R.drawable.humidity, null)
                            , "Humidity", String.valueOf(main.getHumidity())
                                    .concat(context.getResources().getString(R.string.per_cent_text))
                    );
                else if (main.getTemp_min() != 0)
                    updateUiBasedOnWeatherType(holder, context.getResources().getDrawable(R.drawable.temp, null)
                            , "Low", String.valueOf((int) (main.getTemp_min() - Main.KELVIN))
                                    .concat(context.getResources().getString(R.string.celicus_text))
                    );
                else if (main.getTemp_max() != 0)
                    updateUiBasedOnWeatherType(holder, context.getResources().getDrawable(R.drawable.temp, null)
                            , "High", String.valueOf((int) (main.getTemp_max() - Main.KELVIN))
                                    .concat(context.getResources().getString(R.string.celicus_text))
                    );
            }
        }
    }

    /**
     * @param viewToAnimate view to perform animation on
     */
    private void animateItems(View viewToAnimate) {
        ScaleAnimation anim = new ScaleAnimation(
                0.0f
                , 1.0f
                , 0.0f
                , 1.0f
                , Animation.RELATIVE_TO_SELF
                , 0.5f
                , Animation.RELATIVE_TO_SELF
                , 0.5f
        );
        anim.setDuration(500);
        new Thread(() ->
                viewToAnimate.post(() ->
                        viewToAnimate.startAnimation(anim))).start();
    }

    private boolean isCityAdapter() {
        return layoutRequest == CITY_ADAPTER;
    }

    // returns the count of items in the list
    @Override
    public int getItemCount() {
        if (cities != null)
            return cities.size();

        return weatherInfo.size();
    }

    /**
     * updating the UI for weather details list
     *
     * @param holder which contains the views inside the attached layout
     * @param icon   icon for the types of weather data represented
     * @param title  title for the types of weather data represented
     * @param info   data represent a title of weather data represented
     */
    private void updateUiBasedOnWeatherType(ViewHolder holder, Drawable icon, String title, String info) {
        new Thread(() ->
                ((WeatherDetailsActivity) context).runOnUiThread(() -> {
                    holder.infoTitle.setText(title);
                    holder.weatherInfo.setText(info);
                })).start();

        new Thread(() ->
                holder.weatherIcon.post(() ->
                        holder.weatherIcon.setImageDrawable(icon))).start();
    }


    // inner class for initialization of views inside a layout
    class ViewHolder extends RecyclerView.ViewHolder {

        // city item views
        ImageView image;
        TextView name;
        MaterialCardView cityCard;
        ContentLoadingProgressBar progressBar;

        // weather content item views
        TextView infoTitle;
        TextView weatherInfo;
        ImageView weatherIcon;
        ConstraintLayout weatherDataLayout;

        ViewHolder(@NonNull View view) {
            super(view);

            if (isCityAdapter()) {
                progressBar = view.findViewById(R.id.city_image_loader);
                image = view.findViewById(R.id.city_image);
                name = view.findViewById(R.id.city_name);
                cityCard = view.findViewById(R.id.city_card);

            } else {
                weatherDataLayout = view.findViewById(R.id.weather_item_layout);
                infoTitle = view.findViewById(R.id.info_title);
                weatherInfo = view.findViewById(R.id.weather_info);
                weatherIcon = view.findViewById(R.id.info_icon);
            }
        }

    }

}