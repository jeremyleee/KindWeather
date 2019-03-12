package com.tragicfruit.weatherapp.screens

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class WActivity : AppCompatActivity() {

    @IdRes
    protected open val fragmentContainer: Int = android.R.id.content

    fun presentFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainer, fragment)
            .apply {
                if (addToBackStack) {
                    addToBackStack(fragment.tag)
                }
            }
            .commit()
    }

}