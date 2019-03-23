package com.tragicfruit.kindweather.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DisplayUtils {

    private val calendar = Calendar.getInstance()
    private val shortTimeFormatter = DateFormat.getTimeInstance(DateFormat.SHORT)
    private val summaryDateFormatter = SimpleDateFormat("MMM dd", Locale.ENGLISH)

    fun getMeasurementString(value: Float, units: String, decimalPlaces: Int = 1): String {
        return "${value.format(decimalPlaces)}$units"
    }

    fun getTimeString(hour: Int, minute: Int, seconds: Int): String {
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, seconds)

        return shortTimeFormatter.format(calendar.time)
    }

    fun getDateString(date: Date?): String {
        val validDate = date ?: return ""
        return summaryDateFormatter.format(validDate)
    }

}