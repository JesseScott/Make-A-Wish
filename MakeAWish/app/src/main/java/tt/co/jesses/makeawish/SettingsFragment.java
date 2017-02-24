package tt.co.jesses.makeawish;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

/**
 * Created by jessescott on 2017-02-19.
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load Prefs from XML
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    public boolean getDaytimeEnabled() {
        Preference preference = findPreference(getString(R.string.prefs_enable_daytime_alarms));
        boolean daytimeEnabled = preference.isEnabled();
        return daytimeEnabled;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String match = getString(R.string.prefs_enable_daytime_alarms);
        if ( key.equals(match) ) {
            Preference preference = findPreference(key);
            boolean daytimeEnabled = preference.isEnabled();

        }
    }

}
