package tt.co.jesses.makeawish

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.analytics.FirebaseAnalytics
import tt.co.jesses.makeawish.helpers.AlarmHelper

class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var floatingActionButton: FloatingActionButton? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.main_rv) as RecyclerView
        floatingActionButton = findViewById(R.id.main_fab) as FloatingActionButton
        floatingActionButton!!.setOnClickListener { Log.d(TAG, "FAB") }

        FirebaseAnalytics.getInstance(this.applicationContext).setCurrentScreen(this@MainActivity, "MainActivity", MainActivity::class.java.simpleName)

        val alarmHelper = AlarmHelper(applicationContext)
        alarmHelper.setAlarms()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                Log.d(TAG, "Settings selected")
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

}
