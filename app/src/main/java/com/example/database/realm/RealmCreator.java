package com.example.database.realm;

import android.util.Log;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmQuery;

// singleton class for creating Realm instance
public final class RealmCreator<E> {

    private volatile static RealmCreator realmCreator;
    private volatile static Realm realmDatabase;

    private RealmCreator() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name(Constants.NAME)
                .schemaVersion(Constants.VERSION)
                .deleteRealmIfMigrationNeeded()
                .build();

        realmDatabase = Realm.getInstance(realmConfig);
    }

    // encapsulating Realm instance creation
    public static RealmCreator newInstance() {
        if (realmDatabase == null) {
            synchronized (RealmCreator.class) {
                if (realmDatabase == null) {
                    // creating database
                    realmCreator = new RealmCreator();
                }
            }
        }

        return realmCreator;
    }

    public void executeAsync(@NonNull Realm.Transaction transaction, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError) {
        if (realmDatabase.isClosed())
            realmDatabase = Realm.getDefaultInstance();

        realmDatabase.executeTransactionAsync(transaction);
    }

    // for closing the communication with database
    public static void closeRealm() {
        if (realmDatabase != null &&
                !realmDatabase.isClosed() && !realmDatabase.isInTransaction()) {
            Realm.getDefaultInstance();
            realmDatabase.close();
            realmDatabase = null;
            realmCreator = null;

        } else {
            Log.e(RealmCreator.class.getSimpleName(),
                    "closeRealm: realm instance is closed already or transaction not finished yet.");

        }
    }

    public boolean isClosed() {
        return realmDatabase != null && realmDatabase.isClosed();
    }

    public RealmQuery<? extends RealmModel> where(Class<? extends RealmObject> realmWeatherClass) {
        return realmDatabase.where(realmWeatherClass);
    }

    @Retention(value = RetentionPolicy.SOURCE)
    @IntDef(Constants.VERSION)
    @StringDef(Constants.NAME)
    private @interface Constants {
        int VERSION = 1;
        String NAME = "JoWeather.db";
    }
}