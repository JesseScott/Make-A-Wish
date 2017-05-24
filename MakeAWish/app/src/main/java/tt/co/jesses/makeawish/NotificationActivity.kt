package tt.co.jesses.makeawish

import android.app.Activity
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Created by jessescott on 2017-02-22.
 */

class NotificationActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        FirebaseAnalytics.getInstance(this.applicationContext).setCurrentScreen(this@NotificationActivity, "NotificationActivity", NotificationActivity::class.java.simpleName)
    }

}
