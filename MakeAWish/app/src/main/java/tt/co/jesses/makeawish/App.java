package tt.co.jesses.makeawish;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by jessescott on 2017-02-27.
 */

public class App extends Application {

    private static final String TAG = App.class.getSimpleName();
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "Application Created");

        // Context
        mContext = this;

    }

    public static Context getContext() {
        return mContext;
    }


}
