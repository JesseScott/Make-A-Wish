package tt.co.jesses.makeawish.helpers

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.util.*

class CalendarHelperTest {

    @Test
    fun testCalendarsDaytime() {
        val helper = CalendarHelper()
        val calendars = helper.calendarsDaytime

        assertNotNull(calendars)
        assertEquals(8, calendars.size)

        val expectedTimes = arrayOf(
            Pair(10, 10),
            Pair(11, 11),
            Pair(12, 12),
            Pair(13, 11),
            Pair(14, 22),
            Pair(15, 33),
            Pair(16, 44),
            Pair(17, 55)
        )

        for (i in 0 until 8) {
            val cal = calendars[i]
            assertNotNull("Calendar at index $i should not be null", cal)
            assertEquals("Hour mismatch at index $i", expectedTimes[i].first, cal?.get(Calendar.HOUR_OF_DAY))
            assertEquals("Minute mismatch at index $i", expectedTimes[i].second, cal?.get(Calendar.MINUTE))
        }
    }

    @Test
    fun testCalendarsNighttime() {
        val helper = CalendarHelper()
        val calendars = helper.calendarsNighttime

        assertNotNull(calendars)
        assertEquals(8, calendars.size)

        val expectedTimes = arrayOf(
            Pair(22, 10),
            Pair(23, 11),
            Pair(0, 12),
            Pair(1, 11),
            Pair(2, 22),
            Pair(3, 33),
            Pair(4, 44),
            Pair(5, 55)
        )

        for (i in 0 until 8) {
            val cal = calendars[i]
            assertNotNull("Calendar at index $i should not be null", cal)
            assertEquals("Hour mismatch at index $i", expectedTimes[i].first, cal?.get(Calendar.HOUR_OF_DAY))
            assertEquals("Minute mismatch at index $i", expectedTimes[i].second, cal?.get(Calendar.MINUTE))
        }
    }
}
