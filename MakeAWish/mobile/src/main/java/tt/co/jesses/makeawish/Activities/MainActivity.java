package tt.co.jesses.makeawish.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    private final static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

    String[] times =
            {
            "01:11", "02:22", "03:33", "4:44", "5:55",
            "10:10", "11:11", "12:12", "13:11", "14:22",
            "15:33", "16:44", "17:55", "22:10", "23:11"
            };

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

    private void startTimer()
    {
        int millisInAMinute = 60000;
        long time = System.currentTimeMillis();

        final Activity mActivity = MainActivity.this;

        final Runnable update = new Runnable() {
            @Override
            public void run() {

                Date now = new Date();
                String formattedTime = sdf.format(now.getTime());

                Log.w(TAG, "TIME: " + formattedTime );
                Toast.makeText(MainActivity.this, formattedTime, Toast.LENGTH_SHORT).show();
            }
        };

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                mActivity.runOnUiThread(update);
            }
        }, time % millisInAMinute, millisInAMinute);

        update.run();
    }


}
