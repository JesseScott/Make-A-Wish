package tt.co.jesses.makeawish.ui.screens

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tt.co.jesses.makeawish.MainActivity
import tt.co.jesses.makeawish.R

@RunWith(AndroidJUnit4::class)
class MainScreenTest {

    @Rule
    @JvmField
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testAddWishDialogOpensAndContentDescriptionIsCorrect() {
        // Click the FAB using the new localized content description
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.cd_add_wish)).performClick()

        // Verify the dialog title exists
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.dialog_title_make_wish)).assertExists()
    }

    @Test
    fun testEnterWishAndSave() {
        val input = "My wish"
        // Open Dialog
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.cd_add_wish)).performClick()

        // Enter text
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.dialog_label_enter_wish)).performTextInput(input)

        // Perform save and close dialog
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.dialog_btn_save)).performClick()

        // Verify dialog is closed (Title no longer exists)
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.dialog_title_make_wish)).assertDoesNotExist()
    }
}
