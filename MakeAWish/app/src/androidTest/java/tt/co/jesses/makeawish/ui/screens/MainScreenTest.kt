package tt.co.jesses.makeawish.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performImeAction
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainScreenTest {

    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @Test
    fun testAddWishDialogOpensAndContentDescriptionIsCorrect() {
        composeTestRule.setContent {
            MainScreen(onSettingsClick = {})
        }

        // Click the FAB using the new localized content description
        // Note: In a real test we should use the resource ID, but for simplicity here we use the string value
        // that we know we added to strings.xml: "Add a new wish"
        composeTestRule.onNodeWithContentDescription("Add a new wish").performClick()

        // Verify the dialog title exists
        composeTestRule.onNodeWithText("Make a New Wish").assertExists()
    }

    @Test
    fun testEnterWishAndSave() {
        composeTestRule.setContent {
            MainScreen(onSettingsClick = {})
        }

        // Open Dialog
        composeTestRule.onNodeWithContentDescription("Add a new wish").performClick()

        // Enter text
        composeTestRule.onNodeWithText("Enter your wish").performTextInput("My Wish")

        // Perform IME action (Done) which should trigger save and close dialog
        composeTestRule.onNodeWithText("My Wish").performImeAction()

        // Verify dialog is closed (Title no longer exists)
        composeTestRule.onNodeWithText("Make a New Wish").assertDoesNotExist()
    }
}
