package tt.co.jesses.makeawish

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.analytics.FirebaseAnalytics
import tt.co.jesses.makeawish.helpers.AlarmHelper
import tt.co.jesses.makeawish.ui.screens.MainScreen
import tt.co.jesses.makeawish.ui.screens.NotificationScreen
import tt.co.jesses.makeawish.ui.screens.SettingsScreen
import tt.co.jesses.makeawish.ui.theme.MakeAWishTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseAnalytics.getInstance(this.applicationContext).setCurrentScreen(this@MainActivity, "MainActivity", MainActivity::class.java.simpleName)

        val alarmHelper = AlarmHelper(applicationContext)
        alarmHelper.setAlarms()

        val startDestination = if (intent?.getStringExtra("navigation_route") == "notification") {
            "notification"
        } else {
            "main"
        }

        setContent {
            MakeAWishTheme {
                MakeAWishApp(startDestination = startDestination)
            }
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeAWishApp(startDestination: String) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: startDestination

    Scaffold(
        topBar = {
            if (currentRoute == "main") {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    actions = {
                        IconButton(onClick = { navController.navigate("settings") }) {
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
                                "settings" -> stringResource(R.string.settings)
                                "notification" -> "Notification" // TODO resource
                                else -> ""
                            }
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
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
            composable("main") {
                MainScreen(onSettingsClick = { navController.navigate("settings") })
            }
            composable("settings") {
                SettingsScreen()
            }
            composable("notification") {
                NotificationScreen()
            }
        }
    }
}
