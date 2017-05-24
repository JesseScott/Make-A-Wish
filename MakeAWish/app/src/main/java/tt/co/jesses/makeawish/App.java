package tt.co.jesses.makeawish;

import com.google.firebase.analytics.FirebaseAnalytics;

import android.app.Application;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by jessescott on 2017-02-27.
 */

public class App extends Application {

    private static final String TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "Application Created");

        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(getApplicationContext());
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
        firebaseAnalytics.setUserId(Settings.Secure.ANDROID_ID);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null);
    }

}
