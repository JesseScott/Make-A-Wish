package tt.co.jesses.makeawish;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by jessescott on 2017-02-27.
 */

public class PreferenceHelper {

    private static final String TAG = PreferenceHelper.class.getSimpleName();

    private Context mContext;

    public PreferenceHelper() {
        mContext = mContext.getApplicationContext();
    }

    public void setPrefValueByKey(String key, boolean value) {
        Log.d(TAG, "Setting Value of Key " + key + " to value " + value);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getPrefValueByKey(String key) {
        return true;
    }

}
