package tt.co.jesses.makeawish.ui.viewmodels

import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import tt.co.jesses.makeawish.R
import tt.co.jesses.makeawish.domain.repository.SettingsRepository

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private lateinit var viewModel: SettingsViewModel
    private lateinit var mockRepository: SettingsRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = mockk()
        
        // Mock initial values
        every { mockRepository.getPrefValue(R.string.prefs_enable_nighttime_alarms) } returns true
        every { mockRepository.getIntPrefValue(R.string.prefs_evening_cutoff_index, 1) } returns 5
        every { mockRepository.getPrefValue(R.string.prefs_enable_analytics) } returns false
        every { mockRepository.getPrefValue(R.string.prefs_enable_daytime_alarms) } returns true
        
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
        verify { mockRepository.setPrefValue(R.string.prefs_enable_nighttime_alarms, false) }
    }

    @Test
    fun `setCutoffIndex updates value and repository`() {
        viewModel.setCutoffIndex(10)
        assertEquals(10, viewModel.cutoffIndex.value)
        verify { mockRepository.setIntPrefValue(R.string.prefs_evening_cutoff_index, 10) }
    }

    @Test
    fun `setAnalyticsEnabled updates value and repository`() {
        viewModel.setAnalyticsEnabled(true)
        assertEquals(true, viewModel.analyticsEnabled.value)
        verify { mockRepository.setPrefValue(R.string.prefs_enable_analytics, true) }
    }

    @Test
    fun `setDaytimeEnabled updates value and repository`() {
        viewModel.setDaytimeEnabled(false)
        assertEquals(false, viewModel.daytimeEnabled.value)
        verify { mockRepository.setPrefValue(R.string.prefs_enable_daytime_alarms, false) }
    }

    @Test
    fun `resetOnboarding updates repository`() {
        viewModel.resetOnboarding()
        verify { mockRepository.setPrefValue(R.string.prefs_onboarding_completed, false) }
    }

    @Test
    fun `completeOnboarding calls repository`() {
        viewModel.completeOnboarding()
        verify { mockRepository.completeOnboarding() }
    }
}
