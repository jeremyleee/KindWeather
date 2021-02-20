package com.tragicfruit.kindweather.ui.home.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tragicfruit.kindweather.data.NotificationRepository
import com.tragicfruit.kindweather.model.WeatherNotification
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.RealmResults
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    repository: NotificationRepository
) : ViewModel() {

    private val _feedData = MutableLiveData(repository.getAllNotifications())
    val feedData: LiveData<RealmResults<WeatherNotification>> get() = _feedData
}