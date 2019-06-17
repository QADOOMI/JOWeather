package retrofit.handling;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

// singleton class for handling http requests and responses
public final class RetrofitClient {

    // retrofit instance
    private static Retrofit retrofit;
    // debug purpose
    private static final String TAG = RetrofitClient.class.getSimpleName();
    // URL for loading weather API from
    private static final String API_BASE_URL = "http://api.openweathermap.org/data/2.5/";

    private RetrofitClient() {
    }

    // encapsulating the retrofit instance
    public static Retrofit getRetrofit() {
        if (retrofit == null) {

            // build retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // returning the base URL
    public static String getBaseUrl(){
        return retrofit.baseUrl().toString();
    }
}
