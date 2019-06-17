package internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

// class performs in Async manner
public final class InternetChecker extends AsyncTask<Context, Void, Boolean> {
    private static final String TAG = InternetChecker.class.getSimpleName();

    /**
     * @param contexts reference for activity context
     *
     * */
    @Override
    protected Boolean doInBackground(Context... contexts) {
        ConnectivityManager connectivityManager = (ConnectivityManager) contexts[0].getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (connectivityManager != null && networkInfo.isConnected()) {
            Log.d(TAG, "isConnectedToNetwork: YESS");
            return true;
        }
        return false;

    }
}
