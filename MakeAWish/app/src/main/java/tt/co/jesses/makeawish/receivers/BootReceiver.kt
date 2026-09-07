package tt.co.jesses.makeawish.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import tt.co.jesses.makeawish.R
import tt.co.jesses.makeawish.helpers.AlarmHelper
import tt.co.jesses.makeawish.helpers.PreferenceHelper

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == Intent.ACTION_LOCKED_BOOT_COMPLETED ||
            intent.action == Intent.ACTION_MY_PACKAGE_REPLACED
        ) {
            Log.d(TAG, "Boot or package replaced event received: ${intent.action}. Re-scheduling wish alarms.")

            val preferenceHelper = PreferenceHelper(context)
            // Reset daytime and nighttime set flags so alarms will be re-registered
            preferenceHelper.setPrefValueByKey(context.getString(R.string.prefs_daytime_set), false)
            preferenceHelper.setPrefValueByKey(context.getString(R.string.prefs_nighttime_set), false)

            val alarmHelper = AlarmHelper(context)
            alarmHelper.setAlarms()
        }
    }

    companion object {
        private val TAG = BootReceiver::class.java.simpleName
    }
}
