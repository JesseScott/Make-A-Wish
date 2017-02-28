package tt.co.jesses.makeawish.helpers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import tt.co.jesses.makeawish.R;
import tt.co.jesses.makeawish.receivers.AlarmReceiver;

/**
 * Created by jessescott on 2017-02-27.
 */

public class AlarmHelper {

    private static final String TAG = AlarmHelper.class.getSimpleName();

    private Context mContext;
    private CalendarHelper mCalendarHelper;
    private PreferenceHelper mPreferenceHelper;
    private PendingIntent mAlarmIntent;
    private AlarmManager mAlarmManager;


    public AlarmHelper(Context context) {
        mContext = context;

        mCalendarHelper = new CalendarHelper();
        mPreferenceHelper = new PreferenceHelper(mContext);

        Intent intent = new Intent(mContext, AlarmReceiver.class);
        mAlarmIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);

        mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarms() {
        // Test to see if we've already set recurring daytime alarms AND/OR if we have enabled them
        boolean daytimeEnabled = mPreferenceHelper.getPrefValueByKey(mContext.getString(R.string.prefs_enable_daytime_alarms));
        boolean daytimeSet = mPreferenceHelper.getPrefValueByKey(mContext.getString(R.string.prefs_daytime_set));
        if (!daytimeSet && daytimeEnabled) {
            for (Calendar calendar : mCalendarHelper.getCalendarsDaytime()) {
                if (null != calendar) {
                    mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, mAlarmIntent);
                }
            }
            // Set the Preferences
            mPreferenceHelper.setPrefValueByKey(mContext.getString(R.string.prefs_daytime_set), true);
        }

        // Test to see if we've already set recurring nighttime alarms AND/OR if we have enabled them
        boolean nighttimeEnabled = mPreferenceHelper.getPrefValueByKey(mContext.getString(R.string.prefs_enable_nighttime_alarms));
        boolean nighttimeSet = mPreferenceHelper.getPrefValueByKey(mContext.getString(R.string.prefs_nighttime_set));
        if (!nighttimeSet && nighttimeEnabled) {
            for (Calendar calendar : mCalendarHelper.getCalendarsNighttime()) {
                if (null != calendar) {
                    mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, mAlarmIntent);
                }
            }
            // Set the Preferences
            mPreferenceHelper.setPrefValueByKey(mContext.getString(R.string.prefs_nighttime_set), true);
        }
    }



}
