package tt.co.jesses.makeawish;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String PREFS_NAME = "MAW_PREFS";
    private SharedPreferences mPreferences;

    private Calendar[] calendarsNighttime = new Calendar[8];
    // AM - 10:10, 11:11, 12:12, 1:11, 2:22, 3:33, 4:44, 5:55
    private Calendar[] calendarsDaytime = new Calendar[8];
    // PM - 10:10, 11:11, 12:12, 1:11, 2:22, 3:33, 4:44, 5:55

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = getPreferences(MODE_PRIVATE);

        setAlarms();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Log.d(TAG, "Settings selected");
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setAlarms() {

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean daytime = sharedPref.getBoolean(getString(R.string.settings_enable_daytime_alarms), false);


        // Test to see if we've already set recurring daytime alarms AND/OR if we have enabled them
        boolean daytimeEnabled = mPreferences.getBoolean(getString(R.string.settings_enable_daytime_alarms), false);
        boolean daytimeSet = mPreferences.getBoolean(getString(R.string.prefs_daytime_set), false);
//        if (!daytimeSet && daytimeEnabled) {
//            setCalendarsDaytime();
//            for (Calendar calendar : calendarsDaytime) {
//                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
//            }
//            // Set the Preferences
//            mPreferences.edit().putBoolean(getString(R.string.prefs_daytime_set), true).apply();
//        }

        // Test to see if we've already set recurring nighttime alarms AND/OR if we have enabled them
        boolean nighttimeEnabled = mPreferences.getBoolean(getString(R.string.settings_enable_nighttime_alarms), false);
        boolean nighttimeSet = mPreferences.getBoolean(getString(R.string.prefs_nighttime_set), false);
//        if (!nighttimeSet && nighttimeEnabled) {
//            setCalendarsNighttime();
//            for (Calendar calendar : calendarsNighttime) {
//                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
//            }
//            // Set the Preferences
//            mPreferences.edit().putBoolean(getString(R.string.prefs_nighttime_set), true).apply();
//        }


    }

    private void setCalendarsNighttime() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        // 10:10 PM
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 10);
        calendarsDaytime[0] = calendar;
        calendar.clear();

        // 11:11 PM
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 11);
        calendarsDaytime[1] = calendar;
        calendar.clear();

        // 12:12 AM
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 12);
        calendarsNighttime[2] = calendar;
        calendar.clear();

        // 1:11 AM
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 11);
        calendarsNighttime[3] = calendar;
        calendar.clear();

        // 2:22 AM
        calendar.set(Calendar.HOUR_OF_DAY, 2);
        calendar.set(Calendar.MINUTE, 22);
        calendarsNighttime[4] = calendar;
        calendar.clear();

        // 3:33 AM
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE, 33);
        calendarsNighttime[5] = calendar;
        calendar.clear();

        // 4:44 AM
        calendar.set(Calendar.HOUR_OF_DAY, 4);
        calendar.set(Calendar.MINUTE, 44);
        calendarsNighttime[6] = calendar;
        calendar.clear();

        // 5:55 AM
        calendar.set(Calendar.HOUR_OF_DAY, 5);
        calendar.set(Calendar.MINUTE, 55);
        calendarsNighttime[7] = calendar;
        calendar.clear();

    }

    private void setCalendarsDaytime() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        // 10:10 AM
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 10);
        calendarsDaytime[0] = calendar;
        calendar.clear();

        // 11:11 AM
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 11);
        calendarsDaytime[1] = calendar;
        calendar.clear();

        // 12:12 AM
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 12);
        calendarsDaytime[2] = calendar;
        calendar.clear();

        // 1:11 AM
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 11);
        calendarsDaytime[3] = calendar;
        calendar.clear();

        // 2:22 AM
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 22);
        calendarsDaytime[4] = calendar;
        calendar.clear();

        // 3:33 AM
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 33);
        calendarsDaytime[5] = calendar;
        calendar.clear();

        // 4:44 AM
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 44);
        calendarsDaytime[6] = calendar;
        calendar.clear();

        // 5:55
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 55);
        calendarsDaytime[7] = calendar;
        calendar.clear();

    }

}
