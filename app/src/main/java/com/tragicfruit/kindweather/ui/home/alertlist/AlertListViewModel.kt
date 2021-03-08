package com.tragicfruit.kindweather.ui.home.alertlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tragicfruit.kindweather.data.source.AlertRepository
import com.tragicfruit.kindweather.data.WeatherAlert
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlertListViewModel @Inject constructor(
    repository: AlertRepository
) : ViewModel() {

    val alertList: LiveData<List<WeatherAlert>> = repository.getAllAlerts().asLiveData()
}
