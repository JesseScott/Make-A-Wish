package tt.co.jesses.makeawish.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tt.co.jesses.makeawish.data.repository.SettingsRepositoryImpl
import tt.co.jesses.makeawish.domain.repository.SettingsRepository
import tt.co.jesses.makeawish.helpers.AlarmHelper
import tt.co.jesses.makeawish.helpers.PreferenceHelper
import android.content.Context

class SettingsViewModelFactory(
    private val context: Context,
    private val preferenceHelper: PreferenceHelper,
    private val alarmHelper: AlarmHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(
                SettingsRepositoryImpl(context, preferenceHelper, alarmHelper)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
