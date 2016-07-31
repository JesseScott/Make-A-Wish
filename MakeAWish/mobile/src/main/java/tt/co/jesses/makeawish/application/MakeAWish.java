package tt.co.jesses.makeawish.application;

import android.app.Application;
import android.util.Log;


public class MakeAWish extends Application
{
    private static final String TAG = "MAKEAWISH_APP";
    private static MakeAWish mInstance = null;



    @Override
    public void onCreate()
    {
        super.onCreate();

        mInstance = this;

        Log.d(TAG, "Application Start");


    }


    public static MakeAWish getInstance()
    {
        return mInstance;
    }

}
