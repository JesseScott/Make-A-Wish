package tt.co.jesses.makeawish.helpers

import android.content.Context
import android.preference.PreferenceManager
import tt.co.jesses.makeawish.R

/**
 * Created by jessescott on 2017-02-27.
 */

class PreferenceHelper(private val mContext: Context) {

    fun setPrefValueByKey(key: String, value: Boolean) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(mContext)
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()

        if (key == mContext.getString(R.string.prefs_enable_daytime_alarms) || key == mContext.getString(R.string.prefs_enable_nighttime_alarms)) {
            triggerAlarmRegeneration()
        }
    }

    private fun triggerAlarmRegeneration() {
        val alarmHelper = AlarmHelper(mContext)
        alarmHelper.setAlarms()
    }

    fun getPrefValueByKey(key: String): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(mContext)
        return preferences.getBoolean(key, false)
    }

    companion object {
        private val TAG = PreferenceHelper::class.java.simpleName
    }

}
