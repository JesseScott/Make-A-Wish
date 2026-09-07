package tt.co.jesses.makeawish.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import tt.co.jesses.makeawish.BuildConfig
import tt.co.jesses.makeawish.R
import tt.co.jesses.makeawish.ui.components.BedtimeCutoffSlider
import tt.co.jesses.makeawish.ui.viewmodels.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onRestartOnboarding: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val nighttimeEnabled by viewModel.nighttimeEnabled.collectAsState()
    val cutoffIndex by viewModel.cutoffIndex.collectAsState()
    val analyticsEnabled by viewModel.analyticsEnabled.collectAsState()
    val daytimeEnabled by viewModel.daytimeEnabled.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SettingsCheckbox(
            title = stringResource(R.string.settings_enable_daytime_alarms),
            checked = daytimeEnabled,
            onCheckedChange = { viewModel.setDaytimeEnabled(it) }
        )
        SettingsCheckbox(
            title = stringResource(R.string.settings_enable_nighttime_alarms),
            checked = nighttimeEnabled,
            onCheckedChange = { viewModel.setNighttimeEnabled(it) }
        )

        AnimatedVisibility(visible = nighttimeEnabled) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))
                BedtimeCutoffSlider(
                    cutoffIndex = cutoffIndex,
                    onCutoffIndexChange = { newIndex ->
                        viewModel.setCutoffIndex(newIndex)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        SettingsCheckbox(
            title = stringResource(R.string.settings_enable_analytics),
            checked = analyticsEnabled,
            onCheckedChange = { viewModel.setAnalyticsEnabled(it) }
        )

        if (BuildConfig.DEBUG) {
            val onboardingCompletedKey = R.string.prefs_onboarding_completed.toString()
            Spacer(modifier = Modifier.height(28.dp))
            OutlinedButton(
                onClick = {
                    viewModel.resetOnboarding()
                    Toast.makeText(context, "Onboarding reset for debug!", Toast.LENGTH_SHORT).show()
                    onRestartOnboarding?.invoke()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("🛠️ Debug: Reset Onboarding")
            }
        }
    }
}

@Composable
fun SettingsCheckbox(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                role = Role.Checkbox
            )
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
