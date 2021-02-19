package com.tragicfruit.kindweather.ui.home.alertlist

import androidx.lifecycle.ViewModel
import com.tragicfruit.kindweather.data.AlertRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlertListViewModel @Inject constructor(
    repository: AlertRepository
) : ViewModel() {

    val alertList = repository.getAllAlerts()
}
