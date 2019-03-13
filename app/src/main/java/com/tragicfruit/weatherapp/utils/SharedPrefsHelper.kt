package com.tragicfruit.weatherapp.utils

import android.content.Context

object SharedPrefsHelper {

    private const val DEFAULT_SHARED_PREFS = "default"

    private const val KEY_ONBOARDING_COMPLETE = "onboarding-complete"

    fun setOnboardingCompleted(context: Context, completed: Boolean) {
        val sharedPrefs = context.getSharedPreferences(DEFAULT_SHARED_PREFS, Context.MODE_PRIVATE)
        sharedPrefs.edit()
            .putBoolean(KEY_ONBOARDING_COMPLETE, completed)
            .apply()
    }

    fun isOnboardingCompleted(context: Context): Boolean {
        val sharedPrefs = context.getSharedPreferences(DEFAULT_SHARED_PREFS, Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean(KEY_ONBOARDING_COMPLETE, false)
    }

}