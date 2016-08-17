package tt.co.jesses.makeawish.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import tt.co.jesses.makeawish.R;
import tt.co.jesses.makeawish.application.MakeAWish;


public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MAKEAWISH_MAIN";

    String[] times = new String[14];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Log.d(TAG, "MainActivity created");

        MakeAWish.getInstance();

        // Set Times
        setupTimes();

        // Set Job
        startTimer();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Log.d(TAG, "Clicked Settings");
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    // Functions

    private void setupTimes()
    {
        times[0]  = "01:11";
        times[1]  = "02:22";
        times[2]  = "03:33";
        times[3]  = "4:44";
        times[4]  = "5:55";
        times[5]  = "10:10";
        times[6]  = "11:11";
        times[7]  = "12:12";
        times[8]  = "13:11";
        times[9]  = "14:22";
        times[10] = "15:33";
        times[11] = "16:44";
        times[12] = "17:55";
        times[13] = "22:10"; // 10:10 PM
        times[14] = "23:11"; // 11:11 PM
    }

    private void startTimer()
    {
        int milisInAMinute = 60000;
        long time = System.currentTimeMillis();

        final Runnable update = () -> {
            Date now = new Date();
            Log.w(TAG, "TIME: " + now.getTime());

            Calendar c = Calendar.getInstance();
            String formattedTime = String.format("%d:%tM%n", c.get(Calendar.HOUR_OF_DAY), c);
            Log.w(TAG, "CAL: " + formattedTime );
            Toast.makeText(MainActivity.this, formattedTime, Toast.LENGTH_SHORT).show();
        };

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                update.run();
            }
        }, time % milisInAMinute, milisInAMinute);

        update.run();
    }


}
