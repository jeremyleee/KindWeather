package com.tragicfruit.weatherapp.screens

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class WActivity : AppCompatActivity() {

    @IdRes
    protected open val fragmentContainer: Int = android.R.id.content

    fun presentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainer, fragment)
            .addToBackStack(fragment.tag)
            .commit()
    }

}