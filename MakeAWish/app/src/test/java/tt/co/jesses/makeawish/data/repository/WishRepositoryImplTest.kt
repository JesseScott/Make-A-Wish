package tt.co.jesses.makeawish.data.repository

import io.mockk.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import tt.co.jesses.makeawish.data.local.Wish
import tt.co.jesses.makeawish.data.local.WishDao

class WishRepositoryImplTest {

    private lateinit var repository: WishRepositoryImpl
    private lateinit var mockWishDao: WishDao

    @Before
    fun setUp() {
        mockWishDao = mockk()
        repository = WishRepositoryImpl(mockWishDao)
    }

    @Test
    fun `getAllWishes returns flow of wishes`() = runBlocking {
        val wishes = listOf(Wish(id = 1, timestamp = "now", source = "source", wish = "wish 1"),
                             Wish(id = 2, timestamp = "now", source = "source", wish = "wish 2"))
        every { mockWishDao.getAll() } returns flowOf(wishes)

        val result = repository.getAllWishes().toList()
        assertEquals(wishes, result[0])
    }

    @Test
    fun `insertWish calls wishDao insert`() = runBlocking {
        val wish = Wish(id = 1, timestamp = "now", source = "source", wish = "wish 1")
        coEvery { mockWishDao.insert(wish) } just Runs

        repository.insertWish(wish)

        coVerify { mockWishDao.insert(wish) }
    }

    @Test
    fun `deleteWish calls wishDao delete`() = runBlocking {
        val wish = Wish(id = 1, timestamp = "now", source = "source", wish = "wish 1")
        coEvery { mockWishDao.delete(wish) } just Runs

        repository.deleteWish(wish)

        coVerify { mockWishDao.delete(wish) }
    }
}
