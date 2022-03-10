package com.co.wewin.utilities

import java.text.SimpleDateFormat
import java.util.*

object OtherUtilities {
    fun convertToLocalDateFromMilliseconds(date:Date):String{
        val dateFormat: String? = "dd/MM/yyyy hh:mm:ss.SSS"
        val formatter = SimpleDateFormat(dateFormat)
        formatter.timeZone=(TimeZone.getTimeZone("UTC"))
        return formatter.format(date.time)
    }

    fun getDate(milliSeconds: Long, dateFormat: String? = "dd/MM/yyyy hh:mm:ss.SSS"): Date {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return calendar.time
    }
}