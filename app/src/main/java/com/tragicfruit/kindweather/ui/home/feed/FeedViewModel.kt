package com.tragicfruit.kindweather.ui.home.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tragicfruit.kindweather.data.WeatherNotification
import com.tragicfruit.kindweather.data.source.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    repository: NotificationRepository
) : ViewModel() {

    val feedData: LiveData<List<WeatherNotification>> =
        repository.getAllNotifications().asLiveData()
}
