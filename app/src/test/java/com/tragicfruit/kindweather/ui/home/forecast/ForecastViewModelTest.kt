package com.tragicfruit.kindweather.ui.home.forecast

import android.location.Address
import android.location.Geocoder
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tragicfruit.kindweather.MainCoroutineRule
import com.tragicfruit.kindweather.data.ForecastIcon
import com.tragicfruit.kindweather.data.WeatherNotification
import com.tragicfruit.kindweather.data.source.FakeNotificationRepository
import com.tragicfruit.kindweather.getOrAwaitValue
import com.tragicfruit.kindweather.utils.FakeSharedPrefsHelper
import java.io.IOException
import java.util.Date
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ForecastViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val notification = WeatherNotification(
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
    )

    private val repository = FakeNotificationRepository()
    private val sharedPrefsHelper = FakeSharedPrefsHelper()
    private val geocoder: Geocoder = mock()

    // Class to test
    private lateinit var viewModel: ForecastViewModel

    @Before
    fun setUp() {
        repository.notificationMap[notification.id] = notification
        val savedStateHandle = SavedStateHandle(mapOf("notificationId" to notification.id))
        viewModel = ForecastViewModel(savedStateHandle, repository, sharedPrefsHelper, geocoder)
    }

    @Test
    fun testGetUseImperialUnits() {
        sharedPrefsHelper.store[FakeSharedPrefsHelper.KEY_IMPERIAL_UNITS] = false
        assertThat(viewModel.useImperialUnits).isFalse()

        sharedPrefsHelper.store[FakeSharedPrefsHelper.KEY_IMPERIAL_UNITS] = true
        assertThat(viewModel.useImperialUnits).isTrue()
    }

    @Test
    fun testGetNotification() = mainCoroutineRule.runBlockingTest {
        assertThat(viewModel.notification.getOrAwaitValue()).isEqualTo(notification)
    }

    @Test
    fun testGetAddressLabel_success() {
        val mockedAddress: Address = mock()
        whenever(mockedAddress.locality).thenReturn("Test locality")
        whenever(geocoder.getFromLocation(any(), any(), any()))
            .thenReturn(listOf(mockedAddress))

        assertThat(viewModel.addressLabel.getOrAwaitValue()).isEqualTo("Test locality")
    }

    @Test
    fun testGetAddressLabel_error() {
        whenever(geocoder.getFromLocation(any(), any(), any()))
            .thenThrow(IOException("Test exception"))

        assertThat(viewModel.addressLabel.getOrAwaitValue()).isNull()
    }
}
