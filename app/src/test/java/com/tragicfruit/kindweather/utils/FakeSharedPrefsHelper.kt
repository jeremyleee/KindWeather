package com.tragicfruit.kindweather.utils

class FakeSharedPrefsHelper : SharedPrefsHelper {

    val store = mutableMapOf<String, Any>(
        KEY_ONBOARDING_COMPLETE to false,
        KEY_ALERT_HOUR to 0,
        KEY_ALERT_MINUTE to 0,
        KEY_IMPERIAL_UNITS to false
    )

    override fun setOnboardingCompleted(completed: Boolean) {
        store[KEY_ONBOARDING_COMPLETE] = completed
    }

    override fun isOnboardingCompleted(): Boolean {
        return store[KEY_ONBOARDING_COMPLETE] as Boolean
    }

    override fun setAlertHour(hourOfDay: Int) {
        store[KEY_ALERT_HOUR] = hourOfDay
    }

    override fun getAlertHour(): Int {
        return store[KEY_ALERT_HOUR] as Int
    }

    override fun setAlertMinute(minute: Int) {
        store[KEY_ALERT_MINUTE] = minute
    }

    override fun getAlertMinute(): Int {
        return store[KEY_ALERT_MINUTE] as Int
    }

    override fun setUsesImperialUnits(imperialUnits: Boolean) {
        store[KEY_IMPERIAL_UNITS] = imperialUnits
    }

    override fun usesImperialUnits(): Boolean {
        return store[KEY_IMPERIAL_UNITS] as Boolean
    }

    companion object {
        const val KEY_ONBOARDING_COMPLETE = "onboarding-complete"
        const val KEY_ALERT_HOUR = "alert-hour"
        const val KEY_ALERT_MINUTE = "alert-minute"
        const val KEY_IMPERIAL_UNITS = "imperial-units"
    }
}
