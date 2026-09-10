package tt.co.jesses.makeawish.ui.viewmodels

import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.test.*
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import tt.co.jesses.makeawish.data.local.Wish
import tt.co.jesses.makeawish.domain.repository.WishRepository

@OptIn(ExperimentalCoroutinesApi::class)
class WishViewModelTest {

    private lateinit var mockRepository: WishRepository
    private lateinit var viewModel: WishViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = mockk()
        coEvery { mockRepository.insertWish(any()) } just Runs
        coEvery { mockRepository.deleteWish(any()) } just Runs
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `wishes flow contains correct items`() = runTest(testDispatcher) {
        val wishes = listOf(Wish(id = 1, timestamp = "now", source = "source", wish = "wish 1"))
        
        every { mockRepository.getAllWishes() } returns flowOf(wishes)
        viewModel = WishViewModel(mockRepository)

        val result = viewModel.wishes.first()
        assertEquals(wishes, result)
    }

    @Test
    fun `insertWish calls repository insert`() = runTest(testDispatcher) {
        val wish = Wish(id = 1, timestamp = "now", source = "source", wish = "wish 1")
        
        every { mockRepository.getAllWishes() } returns flowOf(emptyList())
        viewModel = WishViewModel(mockRepository)

        viewModel.insertWish(wish)

        coVerify { mockRepository.insertWish(wish) }
    }

    @Test
    fun `deleteWish calls repository delete`() = runTest(testDispatcher) {
        val wish = Wish(id = 1, timestamp = "now", source = "source", wish = "wish 1")
        
        every { mockRepository.getAllWishes() } returns flowOf(emptyList())
        viewModel = WishViewModel(mockRepository)

        viewModel.deleteWish(wish)

        coVerify { mockRepository.deleteWish(wish) }
    }
}
