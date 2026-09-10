package tt.co.jesses.makeawish.domain.repository

import androidx.annotation.StringRes

interface SettingsRepository {
    fun getPrefValue(@StringRes key: Int): Boolean
    fun getIntPrefValue(@StringRes key: Int, defaultValue: Int): Int
    fun setPrefValue(@StringRes key: Int, value: Boolean)
    fun setIntPrefValue(@StringRes key: Int, value: Int)
    fun completeOnboarding()
}
