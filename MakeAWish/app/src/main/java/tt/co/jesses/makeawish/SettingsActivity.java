package tt.co.jesses.makeawish;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by jessescott on 2017-02-19.
 */

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

}
