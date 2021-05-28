package com.example.database.realm;

import android.util.Log;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.realm.Realm;
import io.realm.RealmConfiguration;

// singleton class for creating Realm instance
public final class RealmCreator {

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
    public static Realm newInstance() {
        if (realmDatabase == null) {
            synchronized (Realm.class) {
                if (realmDatabase == null) {
                    // creating database
                    realmCreator = new RealmCreator();
                }
            }
        }

        return realmDatabase;
    }

    // for closing the communication with database
    public static void closeRealm() {
        if (realmDatabase != null &&
                !realmDatabase.isClosed() && !realmDatabase.isInTransaction()) {
            realmDatabase.close();
            realmDatabase = null;

        } else {
            IllegalStateException exception =
                    new IllegalStateException("realm instance is closed already or transaction not finished yet.");
            Log.e(RealmCreator.class.getSimpleName(), "closeRealm: " + exception.getMessage(), exception);

        }
    }

    @Retention(value = RetentionPolicy.SOURCE)
    @IntDef(Constants.VERSION)
    @StringDef(Constants.NAME)
    private @interface Constants {
        int VERSION = 1;
        String NAME = "JoWeather.db";
    }
}