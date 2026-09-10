package tt.co.jesses.makeawish.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tt.co.jesses.makeawish.R
import tt.co.jesses.makeawish.domain.repository.SettingsRepository

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _nighttimeEnabled = MutableStateFlow(false)
    val nighttimeEnabled: StateFlow<Boolean> = _nighttimeEnabled.asStateFlow()

    private val _cutoffIndex = MutableStateFlow(1)
    val cutoffIndex: StateFlow<Int> = _cutoffIndex.asStateFlow()

    private val _analyticsEnabled = MutableStateFlow(false)
    val analyticsEnabled: StateFlow<Boolean> = _analyticsEnabled.asStateFlow()

    private val _daytimeEnabled = MutableStateFlow(false)
    val daytimeEnabled: StateFlow<Boolean> = _daytimeEnabled.asStateFlow()

    init {
        viewModelScope.launch {
            _nighttimeEnabled.value = settingsRepository.getPrefValue(R.string.prefs_enable_nighttime_alarms)
            _cutoffIndex.value = settingsRepository.getIntPrefValue(R.string.prefs_evening_cutoff_index, 1)
            _analyticsEnabled.value = settingsRepository.getPrefValue(R.string.prefs_enable_analytics)
            _daytimeEnabled.value = settingsRepository.getPrefValue(R.string.prefs_enable_daytime_alarms)
        }
    }

    fun setNighttimeEnabled(enabled: Boolean) {
        _nighttimeEnabled.value = enabled
        settingsRepository.setPrefValue(R.string.prefs_enable_nighttime_alarms, enabled)
    }

    fun setCutoffIndex(index: Int) {
        _cutoffIndex.value = index
        settingsRepository.setIntPrefValue(R.string.prefs_evening_cutoff_index, index)
    }

    fun setAnalyticsEnabled(enabled: Boolean) {
        _analyticsEnabled.value = enabled
        settingsRepository.setPrefValue(R.string.prefs_enable_analytics, enabled)
    }

    fun setDaytimeEnabled(enabled: Boolean) {
        _daytimeEnabled.value = enabled
        settingsRepository.setPrefValue(R.string.prefs_enable_daytime_alarms, enabled)
    }

    fun resetOnboarding() {
        settingsRepository.setPrefValue(R.string.prefs_onboarding_completed, false)
    }

    fun completeOnboarding() {
        settingsRepository.completeOnboarding()
    }
}
