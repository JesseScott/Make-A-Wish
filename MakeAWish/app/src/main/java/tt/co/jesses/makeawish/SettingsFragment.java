package tt.co.jesses.makeawish;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

/**
 * Created by jessescott on 2017-02-19.
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //CheckBoxPreference preference = (CheckBoxPreference) findPreference(key);
        Preference preference = findPreference(key);
        if (null != preference && preference instanceof CheckBoxPreference) {
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
            PreferenceHelper preferenceHelper = new PreferenceHelper(getActivity().getApplicationContext());
            preferenceHelper.setPrefValueByKey(key, checkBoxPreference.isChecked());
        }
    }

}
