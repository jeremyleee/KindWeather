package com.tragicfruit.kindweather.ui.home.alertlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.tragicfruit.kindweather.MainCoroutineRule
import com.tragicfruit.kindweather.data.WeatherAlert
import com.tragicfruit.kindweather.data.WeatherAlertType
import com.tragicfruit.kindweather.data.source.FakeAlertRepository
import com.tragicfruit.kindweather.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AlertListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val alerts = listOf(
        WeatherAlert(
            id = "111",
            alertType = WeatherAlertType.ThickJacket,
            priority = 1,
            enabled = true
        ),
        WeatherAlert(
            id = "222",
            alertType = WeatherAlertType.RainJacket,
            priority = 2,
            enabled = true
        )
    )

    private val repository = FakeAlertRepository()

    // Class to test
    private lateinit var viewModel: AlertListViewModel

    @Before
    fun setUp() {
        repository.alertMap.putAll(alerts.map { Pair(it.id, it) })
        viewModel = AlertListViewModel(repository)
    }

    @Test
    fun testGetAlertList() = mainCoroutineRule.runBlockingTest {
        assertThat(viewModel.alertList.getOrAwaitValue()).hasSize(alerts.size)
        assertThat(viewModel.alertList.getOrAwaitValue()).containsExactly(*alerts.toTypedArray())
    }
}
