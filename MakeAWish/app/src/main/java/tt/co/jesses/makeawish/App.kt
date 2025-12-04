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

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = android.app.NotificationManager.IMPORTANCE_DEFAULT
        val channel = android.app.NotificationChannel("default", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: android.app.NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as android.app.NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private val TAG = App::class.java.simpleName
    }

}
