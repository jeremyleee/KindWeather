package com.tragicfruit.kindweather.ui.home.settings

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.tragicfruit.kindweather.getOrAwaitValue
import com.tragicfruit.kindweather.util.FakeSharedPrefsHelper
import com.tragicfruit.kindweather.util.controllers.AlertController
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val alertController: AlertController = mock()
    private val sharedPrefsHelper = FakeSharedPrefsHelper()

    // Class to test
    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setUp() {
        sharedPrefsHelper.store[FakeSharedPrefsHelper.KEY_ALERT_HOUR] = 10
        sharedPrefsHelper.store[FakeSharedPrefsHelper.KEY_ALERT_MINUTE] = 30
        sharedPrefsHelper.store[FakeSharedPrefsHelper.KEY_IMPERIAL_UNITS] = true
        viewModel = SettingsViewModel(alertController, sharedPrefsHelper)
    }

    @Test
    fun testGetAlertTime() {
        assertThat(viewModel.alertTime.getOrAwaitValue()).isEqualTo(AlertTime(10, 30))
    }

    @Test
    fun testGetUseImperialUnits() {
        assertThat(viewModel.useImperialUnits.getOrAwaitValue()).isTrue()
    }

    @Test
    fun testUpdateAlertTime() {
        val mockContext = mock<Context>()
        viewModel.updateAlertTime(mockContext, 3, 33)

        assertThat(viewModel.alertTime.getOrAwaitValue()).isEqualTo(AlertTime(3, 33))
        verify(alertController).scheduleDailyAlert(mockContext)
    }

    @Test
    fun testUpdateUnits() {
        viewModel.updateUnits(false)
        assertThat(viewModel.useImperialUnits.getOrAwaitValue()).isFalse()

        viewModel.updateUnits(true)
        assertThat(viewModel.useImperialUnits.getOrAwaitValue()).isTrue()
    }
}
