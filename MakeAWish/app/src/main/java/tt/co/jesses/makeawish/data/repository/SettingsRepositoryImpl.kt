package tt.co.jesses.makeawish.data.repository

import android.content.Context
import tt.co.jesses.makeawish.R
import tt.co.jesses.makeawish.domain.repository.SettingsRepository
import tt.co.jesses.makeawish.helpers.PreferenceHelper
import tt.co.jesses.makeawish.helpers.AlarmHelper

class SettingsRepositoryImpl(
    private val context: Context,
    private val preferenceHelper: PreferenceHelper,
    private val alarmHelper: AlarmHelper
) : SettingsRepository {

    override fun getPrefValue(key: String): Boolean {
        return preferenceHelper.getPrefValueByKey(key)
    }

    override fun getIntPrefValue(key: String, defaultValue: Int): Int {
        return preferenceHelper.getIntPrefValueByKey(key, defaultValue)
    }

    override fun setPrefValue(key: String, value: Boolean) {
        preferenceHelper.setPrefValueByKey(key, value)
    }

    override fun setIntPrefValue(key: String, value: Int) {
        preferenceHelper.setIntPrefValueByKey(key, value)
    }

    override fun completeOnboarding() {
        preferenceHelper.setPrefValueByKey(R.string.prefs_onboarding_completed.toString(), true)
        alarmHelper.setAlarms()
    }
}
