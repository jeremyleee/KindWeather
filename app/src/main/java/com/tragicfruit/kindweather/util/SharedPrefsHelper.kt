package com.tragicfruit.kindweather.util

interface SharedPrefsHelper {

    fun setOnboardingCompleted(completed: Boolean)

    fun isOnboardingCompleted(): Boolean

    fun setAlertHour(hourOfDay: Int)

    fun getAlertHour(): Int

    fun setAlertMinute(minute: Int)

    fun getAlertMinute(): Int

    fun setUsesImperialUnits(imperialUnits: Boolean)

    fun usesImperialUnits(): Boolean
}
