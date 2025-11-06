package tt.co.jesses.makeawish

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Created by jessescott on 2017-02-19.
 */

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, SettingsFragment())
                .commit()

        FirebaseAnalytics.getInstance(this.applicationContext).setCurrentScreen(this@SettingsActivity, "SettingsActivity", SettingsActivity::class.java.simpleName)

    }

}
