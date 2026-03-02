package tt.co.jesses.makeawish.data.local

import org.junit.Assert.assertEquals
import org.junit.Test
import tt.co.jesses.makeawish.data.WishSource

class WishTest {
    @Test
    fun testWishCreation() {
        val timestamp = "1234567890"
        val source = WishSource.FAB.name
        val wishText = "I wish for world peace"

        val wish = Wish(
            id = 1,
            timestamp = timestamp,
            source = source,
            wish = wishText,
        )

        assertEquals(1, wish.id)
        assertEquals(timestamp, wish.timestamp)
        assertEquals(source, wish.source)
        assertEquals(wishText, wish.wish)
    }

    @Test
    fun testWishDefaultId() {
        val wish = Wish(
            timestamp = "123",
            source = "test",
            wish = "test wish",
        )
        assertEquals(0, wish.id)
    }
}
