package tt.co.jesses.makeawish

import android.app.Application
import android.provider.Settings
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Created by jessescott on 2017-02-27.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "Application Created")

        val firebaseAnalytics = FirebaseAnalytics.getInstance(applicationContext)
        firebaseAnalytics.setAnalyticsCollectionEnabled(true)
        firebaseAnalytics.setUserId(Settings.Secure.ANDROID_ID)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null)
    }

    companion object {
        private val TAG = App::class.java.simpleName
    }

}
