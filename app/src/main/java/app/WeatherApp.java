package app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringDef;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;

import com.example.database.realm.RealmCreator;
import com.example.viewmodel.JoWeatherViewModel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.realm.Realm;

public final class WeatherApp extends Application implements LifecycleObserver {

    private static Context appContext;
    private JoWeatherViewModel joWeatherViewModel;

    public static SharedPreferences getAppPref() {
        return appContext.getSharedPreferences(WeatherApp.class.getSimpleName(), MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getAppPrefEditor() {
        return appContext.getSharedPreferences(WeatherApp.class.getSimpleName(), MODE_PRIVATE).edit();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (joWeatherViewModel == null)
            joWeatherViewModel = JoWeatherViewModel.getInstance(this);

        joWeatherViewModel.loadAndSaveWeatherInfo();

        appContext = getApplicationContext();

        Realm.init(getApplicationContext());

        RealmCreator.newInstance();
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_STOP)
    private void onStop(){
        RealmCreator.closeRealm();
    }

    @Retention(value = RetentionPolicy.SOURCE)
    @StringDef(Constants.todayCastDateMilli)
    public @interface Constants {
        String todayCastDateMilli = "todayCastDateMilli";
    }
}
