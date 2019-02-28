package com.tragicfruit.weatherapp.utils

import android.graphics.Color
import java.util.*

object ColorHelper {

    fun getRandomColor(): Int {
        val random = Random()
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }

}