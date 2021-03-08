package com.tragicfruit.kindweather.ui.home.feed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.tragicfruit.kindweather.MainCoroutineRule
import com.tragicfruit.kindweather.data.ForecastIcon
import com.tragicfruit.kindweather.data.WeatherNotification
import com.tragicfruit.kindweather.data.source.FakeNotificationRepository
import com.tragicfruit.kindweather.getOrAwaitValue
import java.util.Date
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FeedViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val notifications = listOf(
        WeatherNotification(
            id = "111",
            createdAt = Date(),
            description = "description",
            color = 0,
            forecastIcon = ForecastIcon.ClearDay,
            rawTempHigh = null,
            rawTempLow = null,
            rawPrecipProbability = null,
            latitude = 0.0,
            longitude = 0.0
        ),
        WeatherNotification(
            id = "222",
            createdAt = Date(),
            description = "some other description",
            color = 0,
            forecastIcon = ForecastIcon.ClearDay,
            rawTempHigh = 2.3,
            rawTempLow = 20.3,
            rawPrecipProbability = 0.98,
            latitude = 0.0,
            longitude = 0.0
        )
    )

    private val repository = FakeNotificationRepository()

    // Class to test
    private lateinit var viewModel: FeedViewModel

    @Before
    fun setUp() {
        repository.notificationMap.putAll(notifications.map { it.id to it })
        viewModel = FeedViewModel(repository)
    }

    @Test
    fun testGetFeedData() = mainCoroutineRule.runBlockingTest {
        assertThat(viewModel.feedData.getOrAwaitValue())
            .hasSize(notifications.size)
        assertThat(viewModel.feedData.getOrAwaitValue())
            .containsExactly(*notifications.toTypedArray())
    }
}
