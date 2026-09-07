package tt.co.jesses.makeawish.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch
import tt.co.jesses.makeawish.R
import tt.co.jesses.makeawish.helpers.AlarmHelper
import tt.co.jesses.makeawish.helpers.CalendarHelper
import tt.co.jesses.makeawish.helpers.PreferenceHelper
import tt.co.jesses.makeawish.ui.components.BedtimeCutoffSlider

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit
) {
    val context = LocalContext.current
    val preferenceHelper = remember { PreferenceHelper(context) }
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 4 })

    val prefsEnableDaytimeKey = stringResource(R.string.prefs_enable_daytime_alarms)
    val prefsEnableNighttimeKey = stringResource(R.string.prefs_enable_nighttime_alarms)
    val prefsEveningCutoffKey = stringResource(R.string.prefs_evening_cutoff_index)
    val prefsOnboardingCompletedKey = stringResource(R.string.prefs_onboarding_completed)

    var daytimeEnabled by remember {
        mutableStateOf(
            preferenceHelper.getPrefValueByKey(prefsEnableDaytimeKey)
                    || true // Default true for new users
        )
    }

    var nighttimeEnabled by remember {
        mutableStateOf(
            preferenceHelper.getPrefValueByKey(prefsEnableNighttimeKey)
                    || true // Default true for evening angel times
        )
    }

    var cutoffIndex by remember {
        mutableIntStateOf(
            preferenceHelper.getIntPrefValueByKey(prefsEveningCutoffKey, 1) // Default 11:11 PM
        )
    }

    var hasNotificationPermission by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasNotificationPermission = isGranted
        }
    )

    fun savePreferencesAndComplete() {
        preferenceHelper.setPrefValueByKey(prefsEnableDaytimeKey, daytimeEnabled)
        preferenceHelper.setPrefValueByKey(prefsEnableNighttimeKey, nighttimeEnabled)
        preferenceHelper.setIntPrefValueByKey(prefsEveningCutoffKey, cutoffIndex)
        preferenceHelper.setPrefValueByKey(prefsOnboardingCompletedKey, true)

        val alarmHelper = AlarmHelper(context)
        alarmHelper.setAlarms()

        onFinishOnboarding()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Header / Skip Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (pagerState.currentPage < 3) {
                    TextButton(onClick = { savePreferencesAndComplete() }) {
                        Text(text = "Skip", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }

            // Main Pager Content
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    when (page) {
                        0 -> OnboardingWelcomePage()
                        1 -> OnboardingDaytimePage(
                            daytimeEnabled = daytimeEnabled,
                            onDaytimeToggle = { daytimeEnabled = it }
                        )
                        2 -> OnboardingEveningPage(
                            nighttimeEnabled = nighttimeEnabled,
                            onNighttimeToggle = { nighttimeEnabled = it },
                            cutoffIndex = cutoffIndex,
                            onCutoffIndexChange = { cutoffIndex = it }
                        )
                        3 -> OnboardingPermissionsPage(
                            hasPermission = hasNotificationPermission,
                            onRequestPermission = {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                }
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Footer: Pager Indicator Dots & Navigation Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Indicator Dots
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(4) { index ->
                        val isSelected = pagerState.currentPage == index
                        Box(
                            modifier = Modifier
                                .size(if (isSelected) 12.dp else 8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.outlineVariant
                                )
                        )
                    }
                }

                // Next / Get Started Button
                if (pagerState.currentPage < 3) {
                    Button(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = stringResource(R.string.btn_next))
                    }
                } else {
                    Button(
                        onClick = { savePreferencesAndComplete() },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.btn_get_started),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun OnboardingWelcomePage() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(100.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(text = "🌟", fontSize = 52.sp)
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = stringResource(R.string.onboarding_welcome_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = stringResource(R.string.onboarding_welcome_desc),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Angel Times Preview Chips
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("✨ 11:11 AM", "✨ 1:11 PM", "✨ 2:22 PM", "✨ 3:33 PM", "✨ 11:11 PM").forEach { chip ->
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f)
                ) {
                    Text(
                        text = chip,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun OnboardingDaytimePage(
    daytimeEnabled: Boolean,
    onDaytimeToggle: (Boolean) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.onboarding_daytime_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.onboarding_daytime_desc),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Toggle Switch Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Daytime Angel Reminders",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "10:10 AM – 5:55 PM",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = daytimeEnabled,
                    onCheckedChange = onDaytimeToggle
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Daytime Hours Preview List
        AnimatedVisibility(visible = daytimeEnabled) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Scheduled Daytime Times:",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CalendarHelper.DAYTIME_ANGEL_TIME_LABELS.forEach { label ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                        ) {
                            Text(
                                text = "☀️ $label",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OnboardingEveningPage(
    nighttimeEnabled: Boolean,
    onNighttimeToggle: (Boolean) -> Unit,
    cutoffIndex: Int,
    onCutoffIndexChange: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Text(
            text = stringResource(R.string.onboarding_evening_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.onboarding_evening_desc),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Evening Reminders Toggle Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Evening Angel Reminders",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Starts at 10:10 PM",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = nighttimeEnabled,
                    onCheckedChange = onNighttimeToggle
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Animated Bedtime Cutoff Slider
        AnimatedVisibility(visible = nighttimeEnabled) {
            BedtimeCutoffSlider(
                cutoffIndex = cutoffIndex,
                onCutoffIndexChange = onCutoffIndexChange
            )
        }
    }
}

@Composable
private fun OnboardingPermissionsPage(
    hasPermission: Boolean,
    onRequestPermission: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(90.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(text = if (hasPermission) "🔔" else "🔕", fontSize = 44.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.onboarding_permissions_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.onboarding_permissions_desc),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(28.dp))

        if (!hasPermission) {
            OutlinedButton(
                onClick = onRequestPermission,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.btn_grant_permission),
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
            ) {
                Text(
                    text = "✓ Notifications Enabled",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }
        }
    }
}
