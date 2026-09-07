package tt.co.jesses.makeawish.ui.viewmodels

import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import tt.co.jesses.makeawish.R
import tt.co.jesses.makeawish.domain.repository.SettingsRepository

class SettingsViewModelTest {

    private lateinit var viewModel: SettingsViewModel
    private lateinit var mockRepository: SettingsRepository

    @Before
    fun setUp() {
        mockRepository = mockk()
        
        // Mock initial values
        every { mockRepository.getPrefValue(R.string.prefs_enable_nighttime_alarms.toString()) } returns true
        every { mockRepository.getIntPrefValue(R.string.prefs_evening_cutoff_index.toString(), 1) } returns 5
        every { mockRepository.getPrefValue(R.string.prefs_enable_analytics.toString()) } returns false
        every { mockRepository.getPrefValue(R.string.prefs_enable_daytime_alarms.toString()) } returns true
        
        // Mock side effects
        every { mockRepository.setPrefValue(any(), any()) } just Runs
        every { mockRepository.setIntPrefValue(any(), any()) } just Runs
        every { mockRepository.completeOnboarding() } just Runs
        
        viewModel = SettingsViewModel(mockRepository)
    }

    @Test
    fun `initial values are correctly loaded`() {
        assertEquals(true, viewModel.nighttimeEnabled.value)
        assertEquals(5, viewModel.cutoffIndex.value)
        assertEquals(false, viewModel.analyticsEnabled.value)
        assertEquals(true, viewModel.daytimeEnabled.value)
    }

    @Test
    fun `setNighttimeEnabled updates value and repository`() {
        viewModel.setNighttimeEnabled(false)
        assertEquals(false, viewModel.nighttimeEnabled.value)
        verify { mockRepository.setPrefValue(R.string.prefs_enable_nighttime_alarms.toString(), false) }
    }

    @Test
    fun `setCutoffIndex updates value and repository`() {
        viewModel.setCutoffIndex(10)
        assertEquals(10, viewModel.cutoffIndex.value)
        verify { mockRepository.setIntPrefValue(R.string.prefs_evening_cutoff_index.toString(), 10) }
    }

    @Test
    fun `setAnalyticsEnabled updates value and repository`() {
        viewModel.setAnalyticsEnabled(true)
        assertEquals(true, viewModel.analyticsEnabled.value)
        verify { mockRepository.setPrefValue(R.string.prefs_enable_analytics.toString(), true) }
    }

    @Test
    fun `setDaytimeEnabled updates value and repository`() {
        viewModel.setDaytimeEnabled(false)
        assertEquals(false, viewModel.daytimeEnabled.value)
        verify { mockRepository.setPrefValue(R.string.prefs_enable_daytime_alarms.toString(), false) }
    }

    @Test
    fun `resetOnboarding updates repository`() {
        viewModel.resetOnboarding()
        verify { mockRepository.setPrefValue(R.string.prefs_onboarding_completed.toString(), false) }
    }

    @Test
    fun `completeOnboarding calls repository`() {
        viewModel.completeOnboarding()
        verify { mockRepository.completeOnboarding() }
    }
}
