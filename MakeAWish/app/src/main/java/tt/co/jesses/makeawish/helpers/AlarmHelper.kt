package tt.co.jesses.makeawish.helpers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import tt.co.jesses.makeawish.R
import tt.co.jesses.makeawish.receivers.AlarmReceiver
import tt.co.jesses.makeawish.helpers.CalendarHelper
import tt.co.jesses.makeawish.helpers.PreferenceHelper
import java.util.Calendar

/**
 * Created by jessescott on 2017-02-27.
 */

class AlarmHelper(private val mContext: Context) {
    private val mCalendarHelper: CalendarHelper = CalendarHelper()
    private val mPreferenceHelper: PreferenceHelper = PreferenceHelper(mContext)
    private val mAlarmManager: AlarmManager = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setAlarms() {
        val daytimeEnabled = mPreferenceHelper.getPrefValueByKey(mContext.getString(R.string.prefs_enable_daytime_alarms))
        val daytimeSet = mPreferenceHelper.getPrefValueByKey(mContext.getString(R.string.prefs_daytime_set))
        if (!daytimeSet && daytimeEnabled) {
            mCalendarHelper.calendarsDaytime.forEachIndexed { index, calendar ->
                if (calendar != null) {
                    val triggerCalendar = (calendar.clone() as Calendar).apply {
                        if (timeInMillis <= System.currentTimeMillis()) {
                            add(Calendar.DAY_OF_YEAR, 1)
                        }
                    }
                    scheduleExactAlarm(100 + index, triggerCalendar.timeInMillis)
                }
            }
            mPreferenceHelper.setPrefValueByKey(mContext.getString(R.string.prefs_daytime_set), true)
        }

        val nighttimeEnabled = mPreferenceHelper.getPrefValueByKey(mContext.getString(R.string.prefs_enable_nighttime_alarms))
        val nighttimeSet = mPreferenceHelper.getPrefValueByKey(mContext.getString(R.string.prefs_nighttime_set))
        if (!nighttimeSet && nighttimeEnabled) {
            val cutoffIndex = mPreferenceHelper.getIntPrefValueByKey(mContext.getString(R.string.prefs_evening_cutoff_index), 1)
            mCalendarHelper.getFilteredCalendarsNighttime(cutoffIndex).forEachIndexed { index, calendar ->
                val triggerCalendar = (calendar.clone() as Calendar).apply {
                    if (timeInMillis <= System.currentTimeMillis()) {
                        add(Calendar.DAY_OF_YEAR, 1)
                    }
                }
                scheduleExactAlarm(200 + index, triggerCalendar.timeInMillis)
            }
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

    private fun scheduleExactAlarm(requestCode: Int, triggerAtMillis: Long) {
        val intent = Intent(mContext, AlarmReceiver::class.java).apply {
            putExtra(EXTRA_REQUEST_CODE, requestCode)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            mContext,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (mAlarmManager.canScheduleExactAlarms()) {
                mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
            } else {
                mAlarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
            }
        } else {
            mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
        }
    }

    companion object {
        const val EXTRA_REQUEST_CODE = "extra_request_code"
    }
}
