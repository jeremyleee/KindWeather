package com.tragicfruit.kindweather.util

import android.content.Context
import androidx.preference.PreferenceManager

class DefaultSharedPrefsHelper(context: Context) : SharedPrefsHelper {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun setOnboardingCompleted(completed: Boolean) {
        sharedPreferences.edit()
            .putBoolean(KEY_ONBOARDING_COMPLETE, completed)
            .apply()
    }

    override fun isOnboardingCompleted(): Boolean {
        return sharedPreferences.getBoolean(KEY_ONBOARDING_COMPLETE, false)
    }

    override fun setAlertHour(hourOfDay: Int) {
        sharedPreferences.edit()
            .putInt(KEY_ALERT_HOUR, hourOfDay)
            .apply()
    }

    override fun getAlertHour(): Int {
        return sharedPreferences.getInt(KEY_ALERT_HOUR, DEFAULT_ALERT_HOUR)
    }

    override fun setAlertMinute(minute: Int) {
        sharedPreferences.edit()
            .putInt(KEY_ALERT_MINUTE, minute)
            .apply()
    }

    override fun getAlertMinute(): Int {
        return sharedPreferences.getInt(KEY_ALERT_MINUTE, DEFAULT_ALERT_MINUTE)
    }

    override fun setUsesImperialUnits(imperialUnits: Boolean) {
        sharedPreferences.edit()
            .putBoolean(KEY_IMPERIAL_UNITS, imperialUnits)
            .apply()
    }

    override fun usesImperialUnits(): Boolean {
        return sharedPreferences.getBoolean(KEY_IMPERIAL_UNITS, false)
    }

    companion object {
        private const val DEFAULT_ALERT_HOUR = 7
        private const val DEFAULT_ALERT_MINUTE = 0

        private const val KEY_ONBOARDING_COMPLETE = "onboarding-complete"
        private const val KEY_ALERT_HOUR = "alert-hour"
        private const val KEY_ALERT_MINUTE = "alert-minute"
        private const val KEY_IMPERIAL_UNITS = "imperial-units"
    }
}
