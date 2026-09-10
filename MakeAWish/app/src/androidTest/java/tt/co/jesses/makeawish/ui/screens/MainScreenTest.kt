package tt.co.jesses.makeawish.ui.screens

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainScreenTest {

    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun makeAWishFlow() {
        composeTestRule.setContent {
            MainScreen()
        }

        // 1. Click FAB
        composeTestRule.onNodeWithContentDescription("Add").performClick()

        // 2. Check if Dialog appears (title "Record Your Intention")
        composeTestRule.onNodeWithText("Record Your Intention").assertExists()

        // 3. Enter Text
        composeTestRule.onNodeWithText("Write your prayer or intention…").performTextInput("I intend for 100% code coverage")

        // 4. Click Save ("Manifest")
        composeTestRule.onNodeWithText("Manifest").performClick()

        // 5. Verify Dialog Disappears - wait for the dialog to be dismissed
        composeTestRule.waitUntilDoesNotExist(hasText("Add"), timeoutMillis = 10000)
    }
}