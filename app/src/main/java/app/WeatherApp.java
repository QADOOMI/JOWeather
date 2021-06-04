package app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.StringDef;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.database.realm.RealmCreator;
import com.example.viewmodel.JoWeatherViewModel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.realm.Realm;

public final class WeatherApp extends Application
        implements LifecycleObserver {

    private static Context appContext;
    private static WeatherApp weatherApp;
    private JoWeatherViewModel joWeatherViewModel;
    private boolean pause;

    public static SharedPreferences getAppPref() {
        return appContext.getSharedPreferences(WeatherApp.class.getSimpleName(), MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getAppPrefEditor() {
        return appContext.getSharedPreferences(WeatherApp.class.getSimpleName(), MODE_PRIVATE).edit();
    }

    public static WeatherApp getThis() {
        return weatherApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

        Realm.init(getApplicationContext());
        RealmCreator.newInstance();

        weatherApp = this;

        appContext = getApplicationContext();

        joWeatherViewModel = JoWeatherViewModel.getInstance(this);

    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        RealmCreator.closeRealm();
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_STOP)
    private void onStop() {
        pause = true;
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_RESUME)
    private void onResume() {
        RealmCreator.newInstance();

        if (pause) {
            joWeatherViewModel.loadAndSaveWeatherInfo();
        }
    }

    @Retention(value = RetentionPolicy.SOURCE)
    @StringDef(Constants.todayCastDateMilli)
    public @interface Constants {
        String todayCastDateMilli = "todayCastDateMilli";
    }
}
