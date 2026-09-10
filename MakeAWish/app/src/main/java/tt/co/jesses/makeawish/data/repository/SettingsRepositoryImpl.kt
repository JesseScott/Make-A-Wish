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

    override fun getPrefValue(key: Int): Boolean {
        return preferenceHelper.getPrefValueByKey(context.getString(key))
    }

    override fun getIntPrefValue(key: Int, defaultValue: Int): Int {
        return preferenceHelper.getIntPrefValueByKey(context.getString(key), defaultValue)
    }

    override fun setPrefValue(key: Int, value: Boolean) {
        preferenceHelper.setPrefValueByKey(context.getString(key), value)
    }

    override fun setIntPrefValue(key: Int, value: Int) {
        preferenceHelper.setIntPrefValueByKey(context.getString(key), value)
    }

    override fun completeOnboarding() {
        preferenceHelper.setPrefValueByKey(context.getString(R.string.prefs_onboarding_completed), true)
        alarmHelper.setAlarms()
    }
}
