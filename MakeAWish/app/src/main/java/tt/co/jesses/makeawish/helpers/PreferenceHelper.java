package tt.co.jesses.makeawish.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import tt.co.jesses.makeawish.R;

/**
 * Created by jessescott on 2017-02-27.
 */

public class PreferenceHelper {

    private static final String TAG = PreferenceHelper.class.getSimpleName();

    private Context mContext;

    public PreferenceHelper(Context context) {
        mContext = context;
    }

    public void setPrefValueByKey(String key, boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();

        if (key.equals(mContext.getString(R.string.prefs_enable_daytime_alarms)) || key.equals(mContext.getString(R.string.prefs_enable_nighttime_alarms))) {
            triggerAlarmRegeneration();
        }
    }

    private void triggerAlarmRegeneration() {
        AlarmHelper alarmHelper = new AlarmHelper(mContext);
        alarmHelper.setAlarms();
    }

    public boolean getPrefValueByKey(String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getBoolean(key, false);
    }

}
