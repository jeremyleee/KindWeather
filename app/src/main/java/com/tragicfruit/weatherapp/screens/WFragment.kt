package com.tragicfruit.weatherapp.screens

import androidx.fragment.app.Fragment

open class WFragment : Fragment() {

    val baseActivity: WActivity?
        get() = activity as? WActivity

}