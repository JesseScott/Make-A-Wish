package tt.co.jesses.makeawish.domain.repository

import tt.co.jesses.makeawish.data.local.Wish
import kotlinx.coroutines.flow.Flow

interface WishRepository {
    fun getAllWishes(): Flow<List<Wish>>
    suspend fun insertWish(wish: Wish)
    suspend fun deleteWish(wish: Wish)
}
