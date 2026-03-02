package tt.co.jesses.makeawish.ui.screens

import android.content.Context
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

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val preferenceHelper = remember { PreferenceHelper(context) }

    Column(modifier = Modifier.padding(16.dp)) {
        SettingsCheckbox(
            title = stringResource(R.string.settings_enable_notifications),
            key = stringResource(R.string.prefs_enable_notifications),
            defaultValue = true,
            preferenceHelper = preferenceHelper
        )
        SettingsCheckbox(
            title = stringResource(R.string.settings_enable_daytime_alarms),
            key = stringResource(R.string.prefs_enable_daytime_alarms),
            defaultValue = true,
            preferenceHelper = preferenceHelper
        )
        SettingsCheckbox(
            title = stringResource(R.string.settings_enable_nighttime_alarms),
            key = stringResource(R.string.prefs_enable_nighttime_alarms),
            defaultValue = false,
            preferenceHelper = preferenceHelper
        )
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
    preferenceHelper: PreferenceHelper
) {
    var checked by remember {
        mutableStateOf(
            try {
                preferenceHelper.getPrefValueByKey(key)
            } catch (e: Exception) {
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
                onValueChange = {
                    checked = it
                    preferenceHelper.setPrefValueByKey(key, it)
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
