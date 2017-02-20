package tt.co.jesses.makeawish;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by jessescott on 2017-02-19.
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load Prefs from XML
        addPreferencesFromResource(R.xml.preferences);
    }
}
