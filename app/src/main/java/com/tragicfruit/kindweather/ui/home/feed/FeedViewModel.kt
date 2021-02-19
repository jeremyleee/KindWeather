package com.tragicfruit.kindweather.ui.home.feed

import androidx.lifecycle.ViewModel
import com.tragicfruit.kindweather.data.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    repository: NotificationRepository
) : ViewModel() {

    val feedData = repository.getAllNotifications()
}