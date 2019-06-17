package weather.realm;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

// singleton class for creating Realm instance
public class RealmCreator {

    private static Realm realmDatabase;

    private RealmCreator() {

    }

    // encapsulating Realm instance creation
    public static Realm newInstance() {
        if (realmDatabase == null) {

            // creating database
            RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                    .name("weatherInfo.db")
                    .schemaVersion(1)
                    .deleteRealmIfMigrationNeeded()
                    .build();

            realmDatabase = Realm.getInstance(realmConfig);

        }

        return realmDatabase;
    }

    // for closing the communication with database
    public static void closeRealm() {
        if (!realmDatabase.isClosed() && !realmDatabase.isInTransaction()) {
            realmDatabase.close();
            realmDatabase = null;
        } else {
            IllegalStateException exception = new IllegalStateException("realm instance is closed already or transaction not finished yet.");
            Log.e(RealmCreator.class.getSimpleName(), "closeRealm: " + exception.getMessage(), exception);

        }
    }
}