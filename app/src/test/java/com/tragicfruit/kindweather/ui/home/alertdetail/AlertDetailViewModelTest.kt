package com.tragicfruit.kindweather.ui.home.alertdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.tragicfruit.kindweather.MainCoroutineRule
import com.tragicfruit.kindweather.data.*
import com.tragicfruit.kindweather.data.source.FakeAlertRepository
import com.tragicfruit.kindweather.getOrAwaitValue
import com.tragicfruit.kindweather.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AlertDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val alert = WeatherAlert(
        id = "111",
        alertType = WeatherAlertType.ThickJacket,
        priority = 1,
        enabled = true
    )
    private val params = listOf(
        WeatherAlertParam(
            id = "222",
            alertId = alert.id,
            dataType = ForecastDataType.TempLow,
            rawDefaultLowerBound = 1.0,
            rawDefaultUpperBound = 2.0,
            rawLowerBound = 0.0,
            rawUpperBound = 3.0
        )
    )

    private val repository = FakeAlertRepository()

    // Class to test
    private lateinit var viewModel: AlertDetailViewModel

    @Before
    fun setUp() {
        // Setup initial alerts
        repository.alertMap[alert.id] = alert
        repository.paramMap[alert.id] = params.toMutableList()

        val savedStateHandle = SavedStateHandle(mapOf("alertId" to alert.id))
        viewModel = AlertDetailViewModel(savedStateHandle, repository)
    }

    @Test
    fun testGetAlertWithParams() = mainCoroutineRule.runBlockingTest {
        assertThat(viewModel.alertWithParams.getOrAwaitValue())
            .isEqualTo(WeatherAlertWithParams(alert, params))
    }

    @Test
    fun testGetResetButtonEnabled() = mainCoroutineRule.runBlockingTest {
        assertThat(viewModel.resetButtonEnabled.getOrAwaitValue()).isEqualTo(true)
        viewModel.resetParams()
        assertThat(viewModel.resetButtonEnabled.getOrAwaitValue()).isEqualTo(false)
    }

    @Test
    fun testEnableAlert() = mainCoroutineRule.runBlockingTest {
        viewModel.enableAlert(true)
        assertThat(viewModel.alertWithParams.getOrAwaitValue().alert.enabled).isTrue()

        viewModel.enableAlert(false)
        assertThat(viewModel.alertWithParams.getOrAwaitValue().alert.enabled).isFalse()
    }

    @Test
    fun testResetParams() = mainCoroutineRule.runBlockingTest {
        viewModel.alertWithParams.observeForTesting {
            viewModel.resetParams()
            viewModel.alertWithParams.getOrAwaitValue().params.forEach {
                assertThat(it.rawLowerBound).isEqualTo(it.rawDefaultLowerBound)
                assertThat(it.rawUpperBound).isEqualTo(it.rawDefaultUpperBound)
            }
        }
    }

    @Test
    fun testUpdateLowerBound() = mainCoroutineRule.runBlockingTest {
        val param = params.first()
        viewModel.updateLowerBound(param, 40.0)
        viewModel.alertWithParams.getOrAwaitValue().params.find { it.id == param.id }?.let {
            assertThat(it.rawLowerBound).isEqualTo(40.0)
        }
    }

    @Test
    fun testUpdateUpperBound() = mainCoroutineRule.runBlockingTest {
        val param = params.first()
        viewModel.updateUpperBound(param, 50.0)
        viewModel.alertWithParams.getOrAwaitValue().params.find { it.id == param.id }?.let {
            assertThat(it.rawUpperBound).isEqualTo(50.0)
        }
    }
}