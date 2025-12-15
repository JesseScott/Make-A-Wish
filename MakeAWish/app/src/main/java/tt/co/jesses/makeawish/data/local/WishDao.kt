package tt.co.jesses.makeawish.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WishDao {
    @Query("SELECT * FROM wishes")
    fun getAll(): Flow<List<Wish>>

    @Insert
    suspend fun insert(wish: Wish)

    @Delete
    suspend fun delete(wish: Wish)
}
