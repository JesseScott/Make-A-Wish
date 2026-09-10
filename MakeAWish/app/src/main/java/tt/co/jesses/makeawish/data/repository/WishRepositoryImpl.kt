package tt.co.jesses.makeawish.data.repository

import android.content.Context
import tt.co.jesses.makeawish.data.local.Wish
import tt.co.jesses.makeawish.data.local.WishDao
import tt.co.jesses.makeawish.domain.repository.WishRepository
import kotlinx.coroutines.flow.Flow

class WishRepositoryImpl(
    private val wishDao: WishDao
) : WishRepository {

    override fun getAllWishes(): Flow<List<Wish>> {
        return wishDao.getAll()
    }

    override suspend fun insertWish(wish: Wish) {
        wishDao.insert(wish)
    }

    override suspend fun deleteWish(wish: Wish) {
        wishDao.delete(wish)
    }
}
