package tt.co.jesses.makeawish

import android.Manifest
import android.app.Application
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresPermission
import com.google.firebase.analytics.FirebaseAnalytics
import tt.co.jesses.makeawish.data.local.AppDatabase

/**
 * Created by jessescott on 2017-02-27.
 */

class App : Application() {

    companion object {
        private val TAG = App::class.java.simpleName
        lateinit var database: AppDatabase
            private set
    }

    @RequiresPermission(allOf = [Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WAKE_LOCK])
    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "Application Created")
        database = AppDatabase.getDatabase(this)

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

}
