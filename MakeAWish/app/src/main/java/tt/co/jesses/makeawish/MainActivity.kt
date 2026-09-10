package tt.co.jesses.makeawish

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.analytics.FirebaseAnalytics
import tt.co.jesses.makeawish.helpers.AlarmHelper
import androidx.activity.enableEdgeToEdge
import tt.co.jesses.makeawish.helpers.PreferenceHelper
import tt.co.jesses.makeawish.ui.navigation.Screen
import tt.co.jesses.makeawish.ui.screens.MainScreen
import tt.co.jesses.makeawish.ui.screens.NotificationScreen
import tt.co.jesses.makeawish.ui.screens.OnboardingScreen
import tt.co.jesses.makeawish.ui.screens.SettingsScreen
import tt.co.jesses.makeawish.ui.theme.MakeAWishTheme
import tt.co.jesses.makeawish.utils.Constants
import tt.co.jesses.makeawish.ui.viewmodels.SettingsViewModel
import tt.co.jesses.makeawish.ui.viewmodels.SettingsViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var preferenceHelper: PreferenceHelper
    private lateinit var alarmHelper: AlarmHelper

    private val settingsViewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory(applicationContext, preferenceHelper, alarmHelper)
    }

    @RequiresPermission(allOf = [Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WAKE_LOCK])
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, MainActivity::class.java.simpleName)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, MainActivity::class.java.simpleName)
        }
        FirebaseAnalytics.getInstance(this.applicationContext).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        preferenceHelper = PreferenceHelper(this)
        alarmHelper = AlarmHelper(applicationContext)
        
        val navRoute = intent?.getStringExtra(Constants.EXTRA_NAVIGATION_ROUTE)
        val startDestination = when {
            navRoute == Screen.NOTIFICATION.route -> Screen.NOTIFICATION.route
            !preferenceHelper.getPrefValueByKey(getString(R.string.prefs_onboarding_completed)) -> Screen.ONBOARDING.route
            else -> Screen.MAIN.route
        }

        setContent {
            MakeAWishTheme {
                MakeAWishApp(startDestination = startDestination, settingsViewModel = settingsViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeAWishApp(startDestination: String, settingsViewModel: SettingsViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: startDestination

    Scaffold(
        topBar = {
            if (currentRoute == Screen.ONBOARDING.route) {
                // Hide top bar during onboarding
            } else if (currentRoute == Screen.MAIN.route) {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    actions = {
                        IconButton(onClick = { navController.navigate(Screen.SETTINGS.route) }) {
                            Icon(Icons.Filled.Settings, contentDescription = "Settings")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors()
                )
            } else {
                TopAppBar(
                    title = {
                        Text(
                            when (currentRoute) {
                                Screen.SETTINGS.route -> stringResource(R.string.settings)
                                Screen.NOTIFICATION.route -> "Notification" // TODO resource
                                else -> ""
                            }
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors()
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.ONBOARDING.route) {
                OnboardingScreen(
                    viewModel = settingsViewModel,
                    onFinishOnboarding = {
                        navController.navigate(Screen.MAIN.route) {
                            popUpTo(Screen.ONBOARDING.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.MAIN.route) {
                MainScreen()
            }
            composable(Screen.SETTINGS.route) {
                SettingsScreen(
                    viewModel = settingsViewModel,
                    onRestartOnboarding = {
                        navController.navigate(Screen.ONBOARDING.route) {
                            popUpTo(Screen.MAIN.route) { inclusive = false }
                        }
                    }
                )
            }
            composable(Screen.NOTIFICATION.route) {
                NotificationScreen()
            }
        }
    }
}
