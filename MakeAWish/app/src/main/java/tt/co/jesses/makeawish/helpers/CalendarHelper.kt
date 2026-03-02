package tt.co.jesses.makeawish.helpers

import java.util.Calendar

/**
 * Created by jessescott on 2017-02-27.
 */

class CalendarHelper {
    val calendarsNighttime = arrayOfNulls<Calendar>(8)
    // AM - 10:10, 11:11, 12:12, 1:11, 2:22, 3:33, 4:44, 5:55
    // Getters

    val calendarsDaytime = arrayOfNulls<Calendar>(8)
    // PM - 10:10, 11:11, 12:12, 1:11, 2:22, 3:33, 4:44, 5:55

    init {
        setCalendarsDaytime()
        setCalendarsNighttime()
    }

    // Setters

    private fun setCalendarsNighttime() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        // 10:10 PM
        calendarsNighttime[0] = createCalendar(22, 10)

        // 11:11 PM
        calendarsNighttime[1] = createCalendar(23, 11)

        // 12:12 AM
        calendarsNighttime[2] = createCalendar(0, 12)

        // 1:11 AM
        calendarsNighttime[3] = createCalendar(1, 11)

        // 2:22 AM
        calendarsNighttime[4] = createCalendar(2, 22)

        // 3:33 AM
        calendarsNighttime[5] = createCalendar(3, 33)

        // 4:44 AM
        calendarsNighttime[6] = createCalendar(4, 44)

        // 5:55 AM
        calendarsNighttime[7] = createCalendar(5, 55)

    }

    private fun setCalendarsDaytime() {

        // 10:10 AM
        calendarsDaytime[0] = createCalendar(10, 10)

        // 11:11 AM
        calendarsDaytime[1] = createCalendar(11, 11)

        // 12:12 PM
        calendarsDaytime[2] = createCalendar(12, 12)

        // 1:11 PM
        calendarsDaytime[3] = createCalendar(13, 11)

        // 2:22 PM
        calendarsDaytime[4] = createCalendar(14, 22)

        // 3:33 PM
        calendarsDaytime[5] = createCalendar(15, 33)

        // 4:44 PM
        calendarsDaytime[6] = createCalendar(16, 44)

        // 5:55 PM
        calendarsDaytime[7] = createCalendar(17, 55)

    }

    private fun createCalendar(hour: Int, minute: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar
    }
}
