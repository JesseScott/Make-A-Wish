package tt.co.jesses.makeawish.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import tt.co.jesses.makeawish.R
import tt.co.jesses.makeawish.helpers.PreferenceHelper

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.mutableIntStateOf
import tt.co.jesses.makeawish.ui.components.BedtimeCutoffSlider

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val preferenceHelper = remember { PreferenceHelper(context) }
    val nighttimeKey = stringResource(R.string.prefs_enable_nighttime_alarms)
    val cutoffKey = stringResource(R.string.prefs_evening_cutoff_index)

    var nighttimeEnabled by remember {
        mutableStateOf(
            try {
                preferenceHelper.getPrefValueByKey(nighttimeKey)
            } catch (e: Exception) {
                false
            }
        )
    }

    var cutoffIndex by remember {
        mutableIntStateOf(
            try {
                preferenceHelper.getIntPrefValueByKey(cutoffKey, 1)
            } catch (_: Exception) {
                1
            }
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SettingsCheckbox(
            title = stringResource(R.string.settings_enable_daytime_alarms),
            key = stringResource(R.string.prefs_enable_daytime_alarms),
            defaultValue = true,
            preferenceHelper = preferenceHelper
        )
        SettingsCheckbox(
            title = stringResource(R.string.settings_enable_nighttime_alarms),
            key = nighttimeKey,
            defaultValue = false,
            preferenceHelper = preferenceHelper,
            onCheckedChange = { nighttimeEnabled = it }
        )

        AnimatedVisibility(visible = nighttimeEnabled) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))
                BedtimeCutoffSlider(
                    cutoffIndex = cutoffIndex,
                    onCutoffIndexChange = { newIndex ->
                        cutoffIndex = newIndex
                        preferenceHelper.setIntPrefValueByKey(cutoffKey, newIndex)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        SettingsCheckbox(
            title = stringResource(R.string.settings_enable_analytics),
            key = stringResource(R.string.prefs_enable_analytics),
            defaultValue = true,
            preferenceHelper = preferenceHelper
        )
    }
}

@Composable
fun SettingsCheckbox(
    title: String,
    key: String,
    defaultValue: Boolean,
    preferenceHelper: PreferenceHelper,
    onCheckedChange: ((Boolean) -> Unit)? = null
) {
    var checked by remember {
        mutableStateOf(
            try {
                preferenceHelper.getPrefValueByKey(key)
            } catch (e: Exception) {
                Log.d("SettingsScreen", "Error getting preference: $e")
                defaultValue
            }
        )
    }

    // Initialize state from preference on composition
    DisposableEffect(Unit) {
        checked = preferenceHelper.getPrefValueByKey(key)
        onDispose { }
    }

    Row(
        Modifier
            .fillMaxWidth()
            .toggleable(
                value = checked,
                onValueChange = { newValue ->
                    checked = newValue
                    preferenceHelper.setPrefValueByKey(key, newValue)
                    onCheckedChange?.invoke(newValue)
                },
                role = Role.Checkbox
            )
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null // null recommended for accessibility with toggleable modifier
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
