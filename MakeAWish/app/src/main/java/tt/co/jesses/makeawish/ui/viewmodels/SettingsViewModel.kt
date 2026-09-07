package tt.co.jesses.makeawish.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import tt.co.jesses.makeawish.R
import tt.co.jesses.makeawish.domain.repository.SettingsRepository

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _nighttimeEnabled = MutableStateFlow(settingsRepository.getPrefValue(R.string.prefs_enable_nighttime_alarms.toString()))
    val nighttimeEnabled: StateFlow<Boolean> = _nighttimeEnabled.asStateFlow()

    private val _cutoffIndex = MutableStateFlow(settingsRepository.getIntPrefValue(R.string.prefs_evening_cutoff_index.toString(), 1))
    val cutoffIndex: StateFlow<Int> = _cutoffIndex.asStateFlow()

    private val _analyticsEnabled = MutableStateFlow(settingsRepository.getPrefValue(R.string.prefs_enable_analytics.toString()))
    val analyticsEnabled: StateFlow<Boolean> = _analyticsEnabled.asStateFlow()

    private val _daytimeEnabled = MutableStateFlow(settingsRepository.getPrefValue(R.string.prefs_enable_daytime_alarms.toString()))
    val daytimeEnabled: StateFlow<Boolean> = _daytimeEnabled.asStateFlow()

    fun setNighttimeEnabled(enabled: Boolean) {
        _nighttimeEnabled.value = enabled
        settingsRepository.setPrefValue(R.string.prefs_enable_nighttime_alarms.toString(), enabled)
    }

    fun setCutoffIndex(index: Int) {
        _cutoffIndex.value = index
        settingsRepository.setIntPrefValue(R.string.prefs_evening_cutoff_index.toString(), index)
    }

    fun setAnalyticsEnabled(enabled: Boolean) {
        _analyticsEnabled.value = enabled
        settingsRepository.setPrefValue(R.string.prefs_enable_analytics.toString(), enabled)
    }

    fun setDaytimeEnabled(enabled: Boolean) {
        _daytimeEnabled.value = enabled
        settingsRepository.setPrefValue(R.string.prefs_enable_daytime_alarms.toString(), enabled)
    }

    fun resetOnboarding() {
        settingsRepository.setPrefValue(R.string.prefs_onboarding_completed.toString(), false)
    }

    fun completeOnboarding() {
        settingsRepository.completeOnboarding()
    }
}
