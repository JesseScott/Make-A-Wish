package tt.co.jesses.makeawish.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import tt.co.jesses.makeawish.R;

public class SplashActivity extends AppCompatActivity
{
    private static final String TAG = "MAKEAWISH_SPLASH";
    private TextView label;
    private final int WAIT_TIME = 1500;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.d(TAG, "SplashActivity created");

        //Label
        label = (TextView) findViewById(R.id.splash_label);
        label.animate().alpha(1.0f).setDuration(WAIT_TIME);

        // Thread
//        Thread timer = new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    sleep(WAIT_TIME * 2);
//                    Log.d(TAG, "SplashActivity ready to dismiss");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    finish();
//                }
//            }
//        };
//        timer.start();


    }
}
