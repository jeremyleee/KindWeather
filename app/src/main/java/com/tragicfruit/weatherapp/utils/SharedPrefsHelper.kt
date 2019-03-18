package com.tragicfruit.weatherapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object SharedPrefsHelper {

    private const val DEFAULT_ALERT_HOUR = 7
    private const val DEFAULT_ALERT_MINUTE = 0

    private const val KEY_ONBOARDING_COMPLETE = "onboarding-complete"
    private const val KEY_ALERT_HOUR = "alert-hour"
    private const val KEY_ALERT_MINUTE = "alert-minute"
    private const val KEY_METRIC_UNITS = "metric_units"

    lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun setOnboardingCompleted(completed: Boolean) {
        sharedPreferences.edit()
            .putBoolean(KEY_ONBOARDING_COMPLETE, completed)
            .apply()
    }

    fun isOnboardingCompleted(): Boolean {
        return sharedPreferences.getBoolean(KEY_ONBOARDING_COMPLETE, false)
    }

    fun setAlertHour(hourOfDay: Int) {
        sharedPreferences.edit()
            .putInt(KEY_ALERT_HOUR, hourOfDay)
            .apply()
    }

    fun getAlertHour(): Int {
        return sharedPreferences.getInt(KEY_ALERT_HOUR, DEFAULT_ALERT_HOUR)
    }

    fun setAlertMinute(minute: Int) {
        sharedPreferences.edit()
            .putInt(KEY_ALERT_MINUTE, minute)
            .apply()
    }

    fun getAlertMinute(): Int {
        return sharedPreferences.getInt(KEY_ALERT_MINUTE, DEFAULT_ALERT_MINUTE)
    }

    fun setUsesMetricUnits(metricUnits: Boolean) {
        sharedPreferences.edit()
            .putBoolean(KEY_METRIC_UNITS, metricUnits)
            .apply()
    }

    fun usesMetricUnits(): Boolean {
        return sharedPreferences.getBoolean(KEY_METRIC_UNITS, true)
    }

}