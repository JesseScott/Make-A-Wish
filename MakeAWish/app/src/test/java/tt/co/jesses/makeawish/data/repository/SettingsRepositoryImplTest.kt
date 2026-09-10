package tt.co.jesses.makeawish.data.repository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import tt.co.jesses.makeawish.helpers.AlarmHelper
import tt.co.jesses.makeawish.helpers.PreferenceHelper
import android.content.Context
import tt.co.jesses.makeawish.R

class SettingsRepositoryImplTest {

    private lateinit var context: Context
    private lateinit var repository: SettingsRepositoryImpl
    private lateinit var mockPreferenceHelper: PreferenceHelper
    private lateinit var mockAlarmHelper: AlarmHelper

    @Before
    fun setUp() {
        context = mockk(relaxed = true)
        mockPreferenceHelper = mockk(relaxed = true)
        mockAlarmHelper = mockk(relaxed = true)

        // Use constructor injection
        repository = SettingsRepositoryImpl(context, mockPreferenceHelper, mockAlarmHelper)
    }

    @Test
    fun `getPrefValue returns true when preference is true`() {
        val testKey = R.string.prefs_enable_daytime_alarms
        val testString = "prefs_enable_daytime_alarms"
        every { context.getString(testKey) } returns testString
        every { mockPreferenceHelper.getPrefValueByKey(testString) } returns true
        val result = repository.getPrefValue(testKey)
        assertEquals(true, result)
        verify { mockPreferenceHelper.getPrefValueByKey(testString) }
    }

    @Test
    fun `getIntPrefValue returns value when preference is set`() {
        val testKey = R.string.prefs_enable_daytime_alarms
        val testString = "prefs_enable_daytime_alarms"
        every { context.getString(testKey) } returns testString
        every { mockPreferenceHelper.getIntPrefValueByKey(testString, 0) } returns 42
        val result = repository.getIntPrefValue(testKey, 0)
        assertEquals(42, result)
        verify { mockPreferenceHelper.getIntPrefValueByKey(testString, 0) }
    }

    @Test
    fun `setPrefValue calls preferenceHelper`() {
        val testKey = R.string.prefs_enable_daytime_alarms
        val testString = "prefs_enable_daytime_alarms"
        every { context.getString(testKey) } returns testString
        repository.setPrefValue(testKey, true)
        verify { mockPreferenceHelper.setPrefValueByKey(testString, true) }
    }

    @Test
    fun `setPrefValue calls AlarmHelper when completeOnboarding is called`() {
        val testKey = R.string.prefs_onboarding_completed
        val testString = "prefs_onboarding_completed"
        every { context.getString(testKey) } returns testString
        repository.completeOnboarding()
        verify { mockPreferenceHelper.setPrefValueByKey(testString, true) }
        verify { mockAlarmHelper.setAlarms() }
    }
}
