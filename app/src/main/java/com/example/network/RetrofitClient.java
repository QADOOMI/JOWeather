package com.example.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.remoteds.WeatherService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

// singleton class for handling http requests and responses
public final class RetrofitClient {

    // retrofit instance
    private static volatile Retrofit retrofit;
    // debug purpose
    private static final String TAG = RetrofitClient.class.getSimpleName();
    // URL for loading weather API from
    private static final String API_BASE_URL = "http://api.openweathermap.org/data/2.5/";

    public final static String API_KEY = "02f3efe3cdf397ad6b96ad5b64d273d8";

    private RetrofitClient() {
        // build retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    @Nullable
    public WeatherService createService(@NonNull Class<?> service) {
        if (service == WeatherService.class)
            return (WeatherService) retrofit.create(service);

        return null;
    }

    // returning the base URL
    public static String getBaseUrl() {
        return retrofit.baseUrl().toString();
    }

    public interface INSTANCE {
        // build retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
    }
}
