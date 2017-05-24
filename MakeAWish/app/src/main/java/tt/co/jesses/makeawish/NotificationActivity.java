package tt.co.jesses.makeawish;

import com.google.firebase.analytics.FirebaseAnalytics;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by jessescott on 2017-02-22.
 */

public class NotificationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        FirebaseAnalytics.getInstance(this.getApplicationContext()).setCurrentScreen(NotificationActivity.this, "NotificationActivity", NotificationActivity.class.getSimpleName());
    }

}
