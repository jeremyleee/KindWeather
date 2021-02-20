package com.tragicfruit.kindweather.ui.home.alertlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tragicfruit.kindweather.data.AlertRepository
import com.tragicfruit.kindweather.model.WeatherAlert
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.RealmResults
import javax.inject.Inject

@HiltViewModel
class AlertListViewModel @Inject constructor(
    repository: AlertRepository
) : ViewModel() {

    private val _alertList = MutableLiveData(repository.getAllAlerts())
    val alertList: LiveData<RealmResults<WeatherAlert>> get() = _alertList
}
