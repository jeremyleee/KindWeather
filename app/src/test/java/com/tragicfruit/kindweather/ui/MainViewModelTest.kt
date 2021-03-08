package com.tragicfruit.kindweather.ui

import com.google.common.truth.Truth.assertThat
import com.tragicfruit.kindweather.MainCoroutineRule
import com.tragicfruit.kindweather.data.source.FakeAlertRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val repository = FakeAlertRepository()

    // Class to test
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel(repository)
    }

    @Test
    fun testCreateInitialAlerts() = mainCoroutineRule.runBlockingTest {
        viewModel.createInitialAlerts()
        assertThat(repository.getAlertCount()).isGreaterThan(0)
    }

    @Test
    fun testCreateInitialAlerts_subsequentCalls() = mainCoroutineRule.runBlockingTest {
        viewModel.createInitialAlerts()

        val alertCount = repository.getAlertCount()

        // Test that alerts aren't created again
        viewModel.createInitialAlerts()
        assertThat(repository.getAlertCount()).isEqualTo(alertCount)
    }
}
