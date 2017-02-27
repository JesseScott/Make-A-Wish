package tt.co.jesses.makeawish;

import android.app.Application;
import android.util.Log;

/**
 * Created by jessescott on 2017-02-27.
 */

public class App extends Application {

    private static final String TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {
        Log.d(TAG, "Application Created");
        super.onCreate();
    }


}
