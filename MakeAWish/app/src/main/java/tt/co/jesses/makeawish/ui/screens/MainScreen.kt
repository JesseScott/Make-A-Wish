package tt.co.jesses.makeawish.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tt.co.jesses.makeawish.App
import tt.co.jesses.makeawish.R
import tt.co.jesses.makeawish.data.WishSource
import tt.co.jesses.makeawish.data.local.Wish

@Composable
fun MainScreen(onSettingsClick: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    var wishText by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current

    val saveWish = {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        coroutineScope.launch {
            val wish = Wish(
                timestamp = System.currentTimeMillis().toString(),
                source = WishSource.FAB.name,
                wish = wishText
            )
            withContext(Dispatchers.IO) {
                App.database.wishDao().insert(wish)
            }
            showDialog = false
            wishText = "" // Reset text
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.dialog_title_make_wish)) },
            text = {
                TextField(
                    value = wishText,
                    onValueChange = { wishText = it },
                    label = { Text(stringResource(R.string.dialog_label_enter_wish)) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { saveWish() })
                )
            },
            confirmButton = {
                Button(
                    onClick = { saveWish() }
                ) {
                    Text(stringResource(R.string.dialog_btn_save))
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.dialog_btn_cancel))
                }
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    showDialog = true
                },
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.cd_add_wish)
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .align(Alignment.TopCenter)
            )

            // Placeholder for RecyclerView
            Box(modifier = Modifier.fillMaxSize())
        }
    }
}
