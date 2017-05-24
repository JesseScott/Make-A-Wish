package tt.co.jesses.makeawish.helpers

import java.util.*

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
        calendar.set(Calendar.HOUR_OF_DAY, 22)
        calendar.set(Calendar.MINUTE, 10)
        calendarsNighttime[0] = calendar
        calendar.clear()

        // 11:11 PM
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 11)
        calendarsNighttime[1] = calendar
        calendar.clear()

        // 12:12 AM
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 12)
        calendarsNighttime[2] = calendar
        calendar.clear()

        // 1:11 AM
        calendar.set(Calendar.HOUR_OF_DAY, 1)
        calendar.set(Calendar.MINUTE, 11)
        calendarsNighttime[3] = calendar
        calendar.clear()

        // 2:22 AM
        calendar.set(Calendar.HOUR_OF_DAY, 2)
        calendar.set(Calendar.MINUTE, 22)
        calendarsNighttime[4] = calendar
        calendar.clear()

        // 3:33 AM
        calendar.set(Calendar.HOUR_OF_DAY, 3)
        calendar.set(Calendar.MINUTE, 33)
        calendarsNighttime[5] = calendar
        calendar.clear()

        // 4:44 AM
        calendar.set(Calendar.HOUR_OF_DAY, 4)
        calendar.set(Calendar.MINUTE, 44)
        calendarsNighttime[6] = calendar
        calendar.clear()

        // 5:55 AM
        calendar.set(Calendar.HOUR_OF_DAY, 5)
        calendar.set(Calendar.MINUTE, 55)
        calendarsNighttime[7] = calendar
        calendar.clear()

    }

    private fun setCalendarsDaytime() {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        // 10:10 AM
        calendar.set(Calendar.HOUR_OF_DAY, 10)
        calendar.set(Calendar.MINUTE, 10)
        calendarsDaytime[0] = calendar
        calendar.clear()

        // 11:11 AM
        calendar.set(Calendar.HOUR_OF_DAY, 11)
        calendar.set(Calendar.MINUTE, 11)
        calendarsDaytime[1] = calendar
        calendar.clear()

        // 12:12 AM
        calendar.set(Calendar.HOUR_OF_DAY, 12)
        calendar.set(Calendar.MINUTE, 12)
        calendarsDaytime[2] = calendar
        calendar.clear()

        // 1:11 AM
        calendar.set(Calendar.HOUR_OF_DAY, 13)
        calendar.set(Calendar.MINUTE, 11)
        calendarsDaytime[3] = calendar
        calendar.clear()

        // 2:22 AM
        calendar.set(Calendar.HOUR_OF_DAY, 14)
        calendar.set(Calendar.MINUTE, 22)
        calendarsDaytime[4] = calendar
        calendar.clear()

        // 3:33 AM
        calendar.set(Calendar.HOUR_OF_DAY, 15)
        calendar.set(Calendar.MINUTE, 33)
        calendarsDaytime[5] = calendar
        calendar.clear()

        // 4:44 AM
        calendar.set(Calendar.HOUR_OF_DAY, 16)
        calendar.set(Calendar.MINUTE, 44)
        calendarsDaytime[6] = calendar
        calendar.clear()

        // 5:55
        calendar.set(Calendar.HOUR_OF_DAY, 17)
        calendar.set(Calendar.MINUTE, 55)
        calendarsDaytime[7] = calendar
        calendar.clear()

    }

    companion object {
        private val TAG = CalendarHelper::class.java.simpleName
    }

}
