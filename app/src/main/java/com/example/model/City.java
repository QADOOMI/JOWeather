package com.example.model;

import androidx.annotation.StringDef;

import com.example.database.model.RealmWeather;
import com.example.joweather.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class City {

    private String name;
    private int image;


    public City(String name, int image) {

        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", image=" + image +
                '}';
    }

    @Retention(value = RetentionPolicy.SOURCE)
    @StringDef(value = {Constants.AMMAN, Constants.IRBID, Constants.AQABA, Constants.REQUESTED_CITY
            , Constants.AMMAN_ID, Constants.IRBID_ID, Constants.AQABA_ID}, open = true)
    public @interface Constants {
        String AMMAN_ID = "250441";
        String IRBID_ID = "248944";
        String AQABA_ID = "443122";

        String REQUESTED_CITY = "city";
        String AMMAN = "Amman";
        String IRBID = "Irbid";
        String AQABA = "Aqaba";

        // coord sorted in the array with latitude then longtitude
        HashMap<String, float[]> coordinates = new HashMap<String, float[]>() {{
            put(AMMAN, new float[]{31.955219f, 35.94503f});
            put(IRBID, new float[]{32.5f, 35.833328f});
            put(AQABA, new float[]{29.75f, 35.333328f});
        }};
    }
}