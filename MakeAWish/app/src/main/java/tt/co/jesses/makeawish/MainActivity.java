package tt.co.jesses.makeawish;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Calendar[] calendarsAM = new Calendar[6];
    // AM - 12:12, 1:11, 2:22, 3:33, 4:44, 5:55
    private Calendar[] calendarsPM = new Calendar[6];
    // PM - 12:12, 1:11, 2:22, 3:33, 4:44, 5:55

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // https://developer.android.com/guide/topics/ui/notifiers/notifications.html
        // https://developer.android.com/training/scheduling/alarms.html

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 15);
        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);

        setCalendarsPM();
        for (Calendar calendar : calendarsPM) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast);
        }

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

    private void setCalendarsAM() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        // 12:12 AM
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 12);
        calendarsAM[0] = calendar;
        calendar.clear();

        // 1:11 AM
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 11);
        calendarsAM[1] = calendar;
        calendar.clear();

        // 2:22 AM
        calendar.set(Calendar.HOUR_OF_DAY, 2);
        calendar.set(Calendar.MINUTE, 22);
        calendarsAM[3] = calendar;
        calendar.clear();

        // 3:33 AM
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE, 33);
        calendarsAM[4] = calendar;
        calendar.clear();

        // 4:44 AM
        calendar.set(Calendar.HOUR_OF_DAY, 4);
        calendar.set(Calendar.MINUTE, 44);
        calendarsAM[5] = calendar;
        calendar.clear();

        // 5:55
        calendar.set(Calendar.HOUR_OF_DAY, 5);
        calendar.set(Calendar.MINUTE, 55);
        calendarsAM[6] = calendar;
        calendar.clear();

    }

    private void setCalendarsPM() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        // 12:12 AM
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 12);
        calendarsPM[0] = calendar;
        calendar.clear();

        // 1:11 AM
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 11);
        calendarsPM[1] = calendar;
        calendar.clear();

        // 2:22 AM
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 22);
        calendarsPM[2] = calendar;
        calendar.clear();

        // 3:33 AM
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 33);
        calendarsPM[3] = calendar;
        calendar.clear();

        // 4:44 AM
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 44);
        calendarsPM[4] = calendar;
        calendar.clear();

        // 5:55
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 55);
        calendarsPM[5] = calendar;
        calendar.clear();

    }

}
