package tt.co.jesses.makeawish;

import com.google.firebase.analytics.FirebaseAnalytics;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by jessescott on 2017-02-19.
 */

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

        FirebaseAnalytics.getInstance(this.getApplicationContext()).setCurrentScreen(SettingsActivity.this, "SettingsActivity", SettingsActivity.class.getSimpleName());

    }

}
