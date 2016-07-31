package tt.co.jesses.makeawish.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends Activity
{
    private static final String TAG = "MAKEAWISH_MAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "MainActivity created");

    }
}
