package tt.co.jesses.makeawish

import android.os.Bundle
import android.preference.PreferenceActivity
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Created by jessescott on 2017-02-19.
 */

class SettingsActivity : PreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, SettingsFragment())
                .commit()

        FirebaseAnalytics.getInstance(this.applicationContext).setCurrentScreen(this@SettingsActivity, "SettingsActivity", SettingsActivity::class.java.simpleName)

    }

}
