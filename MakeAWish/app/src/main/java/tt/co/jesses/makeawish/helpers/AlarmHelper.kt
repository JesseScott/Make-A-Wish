package tt.co.jesses.makeawish.helpers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import tt.co.jesses.makeawish.R
import tt.co.jesses.makeawish.receivers.AlarmReceiver

/**
 * Created by jessescott on 2017-02-27.
 */

class AlarmHelper(private val mContext: Context) {
    private val mCalendarHelper: CalendarHelper = CalendarHelper()
    private val mPreferenceHelper: PreferenceHelper = PreferenceHelper(mContext)
    private val mAlarmIntent: PendingIntent
    private val mAlarmManager: AlarmManager


    init {
        val intent = Intent(mContext, AlarmReceiver::class.java)
        mAlarmIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0)
        mAlarmManager = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    fun setAlarms() {
        // Test to see if we've already set recurring daytime alarms AND/OR if we have enabled them
        val daytimeEnabled = mPreferenceHelper.getPrefValueByKey(mContext.getString(R.string.prefs_enable_daytime_alarms))
        val daytimeSet = mPreferenceHelper.getPrefValueByKey(mContext.getString(R.string.prefs_daytime_set))
        if (!daytimeSet && daytimeEnabled) {
            for (calendar in mCalendarHelper.calendarsDaytime) {
                if (null != calendar) {
                    mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, mAlarmIntent)
                }
            }
            // Set the Preferences
            mPreferenceHelper.setPrefValueByKey(mContext.getString(R.string.prefs_daytime_set), true)
        }

        // Test to see if we've already set recurring nighttime alarms AND/OR if we have enabled them
        val nighttimeEnabled = mPreferenceHelper.getPrefValueByKey(mContext.getString(R.string.prefs_enable_nighttime_alarms))
        val nighttimeSet = mPreferenceHelper.getPrefValueByKey(mContext.getString(R.string.prefs_nighttime_set))
        if (!nighttimeSet && nighttimeEnabled) {
            for (calendar in mCalendarHelper.calendarsNighttime) {
                if (null != calendar) {
                    mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, mAlarmIntent)
                }
            }
            // Set the Preferences
            mPreferenceHelper.setPrefValueByKey(mContext.getString(R.string.prefs_nighttime_set), true)
        }

        // Log
        val bundle = Bundle()
        bundle.putBoolean(mContext.getString(R.string.prefs_enable_daytime_alarms), daytimeEnabled)
        bundle.putBoolean(mContext.getString(R.string.prefs_daytime_set), daytimeSet)
        bundle.putBoolean(mContext.getString(R.string.prefs_enable_nighttime_alarms), nighttimeEnabled)
        bundle.putBoolean(mContext.getString(R.string.prefs_nighttime_set), nighttimeSet)
        FirebaseAnalytics.getInstance(mContext).logEvent(mContext.getString(R.string.prefs_log_event), bundle)

    }

    companion object {
        private val TAG = AlarmHelper::class.java.simpleName
    }


}
