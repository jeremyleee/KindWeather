package com.tragicfruit.kindweather.screens

import androidx.fragment.app.Fragment

open class WFragment : Fragment() {

    val baseActivity: WActivity?
        get() = activity as? WActivity

}