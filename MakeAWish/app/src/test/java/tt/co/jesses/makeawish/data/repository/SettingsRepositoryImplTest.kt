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
        every { mockPreferenceHelper.getPrefValueByKey(any()) } returns true
        val result = repository.getPrefValue("test_key")
        assertEquals(true, result)
        verify { mockPreferenceHelper.getPrefValueByKey("test_key") }
    }

    @Test
    fun `getIntPrefValue returns value when preference is set`() {
        every { mockPreferenceHelper.getIntPrefValueByKey(any(), any()) } returns 42
        val result = repository.getIntPrefValue("test_key", 0)
        assertEquals(42, result)
        verify { mockPreferenceHelper.getIntPrefValueByKey("test_key", 0) }
    }

    @Test
    fun `setPrefValue calls preferenceHelper`() {
        repository.setPrefValue("test_key", true)
        verify { mockPreferenceHelper.setPrefValueByKey("test_key", true) }
    }

    @Test
    fun `setPrefValue calls AlarmHelper when completeOnboarding is called`() {
        repository.completeOnboarding()
        verify { mockPreferenceHelper.setPrefValueByKey(R.string.prefs_onboarding_completed.toString(), true) }
        verify { mockAlarmHelper.setAlarms() }
    }
}
