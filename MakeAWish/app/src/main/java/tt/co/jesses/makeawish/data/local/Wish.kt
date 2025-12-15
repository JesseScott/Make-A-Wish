package tt.co.jesses.makeawish.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishes")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timestamp: String,
    val source: String,
    val wish: String
)
