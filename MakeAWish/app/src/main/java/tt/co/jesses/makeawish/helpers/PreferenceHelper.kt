package tt.co.jesses.makeawish.helpers


import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import tt.co.jesses.makeawish.R

/**
 * Created by jessescott on 2017-02-27.
 */

class PreferenceHelper(private val mContext: Context) {

    companion object {
        @Volatile
        private var encryptedPrefs: SharedPreferences? = null

        private fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
            return encryptedPrefs ?: synchronized(this) {
                encryptedPrefs ?: run {
                    // Use applicationContext to avoid memory leaks in singleton
                    val appContext = context.applicationContext
                    val prefs = createEncryptedSharedPreferences(appContext)
                    migrateToEncrypted(appContext, prefs)
                    encryptedPrefs = prefs
                    prefs
                }
            }
        }

        private fun createEncryptedSharedPreferences(context: Context): SharedPreferences {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            return EncryptedSharedPreferences.create(
                context,
                "secret_shared_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }

        private fun migrateToEncrypted(context: Context, encryptedPrefs: SharedPreferences) {
            val defaultPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            // Check if we need to migrate: Default prefs has content, Encrypted is empty
            if (defaultPrefs.all.isNotEmpty() && encryptedPrefs.all.isEmpty()) {
                val editor = encryptedPrefs.edit()
                for ((key, value) in defaultPrefs.all) {
                    when (value) {
                        is Boolean -> editor.putBoolean(key, value)
                        is String -> editor.putString(key, value)
                        is Int -> editor.putInt(key, value)
                        is Float -> editor.putFloat(key, value)
                        is Long -> editor.putLong(key, value)
                        is Set<*> -> {
                             @Suppress("UNCHECKED_CAST")
                             editor.putStringSet(key, value as Set<String>)
                        }
                    }
                }
                editor.apply()
                defaultPrefs.edit().clear().apply()
            }
        }
    }

    fun setPrefValueByKey(key: String, value: Boolean) {
        val preferences = getEncryptedSharedPreferences(mContext)
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()

        if (key == mContext.getString(R.string.prefs_enable_daytime_alarms) || key == mContext.getString(R.string.prefs_enable_nighttime_alarms)) {
            triggerAlarmRegeneration()
        }
    }

    private fun triggerAlarmRegeneration() {
        val alarmHelper = AlarmHelper(mContext)
        alarmHelper.setAlarms()
    }

    fun getPrefValueByKey(key: String): Boolean {
        val preferences = getEncryptedSharedPreferences(mContext)
        return preferences.getBoolean(key, false)
    }
}
