package com.tragicfruit.weatherapp.screens.alert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tragicfruit.weatherapp.R
import com.tragicfruit.weatherapp.screens.WFragment

class AlertListFragment : WFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alert_list, container, false)
    }

}