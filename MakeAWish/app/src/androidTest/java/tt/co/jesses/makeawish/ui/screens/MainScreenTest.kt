package tt.co.jesses.makeawish.ui.screens

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
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
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun makeAWishFlow() {
        composeTestRule.setContent {
            MainScreen(onSettingsClick = {})
        }

        // 1. Click FAB
        composeTestRule.onNodeWithContentDescription("Add").performClick()

        // 2. Check if Dialog appears (title "Make a New Wish")
        composeTestRule.onNodeWithText("Make a New Wish").assertExists()

        // 3. Enter Text
        composeTestRule.onNodeWithText("Enter your wish").performTextInput("I wish for 100% code coverage")

        // 4. Click Save
        composeTestRule.onNodeWithText("Save").performClick()

        // 5. Verify Dialog Disappears
        // The dialog dismissal happens after a database insert in a coroutine, so we must wait.
        composeTestRule.waitUntilDoesNotExist(hasText("Make a New Wish"), timeoutMillis = 5000)
    }
}
