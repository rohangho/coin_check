package com.example.coin

import com.example.coin.repository.CoinDataRepository
import com.example.coin.viewmodel.HomeViewModel
import com.example.coin.viewmodel.ScreenState
import com.example.coin.viewmodel.UIEvent
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


class HomeViewModelText {

    @RelaxedMockK
    lateinit var repository: CoinDataRepository

    private lateinit var homeViewModel: HomeViewModel
    val dispatcher = TestCoroutineDispatcher()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        homeViewModel = HomeViewModel(repository)

        Dispatchers.setMain(dispatcher)

    }


    @Test
    fun testProcessUiEvent() {

        every { repository.getData() } returns flow { ScreenState.SuccessData(listOf()) }
        Dispatchers.setMain(StandardTestDispatcher())
        homeViewModel.processEvent(UIEvent.BackToOriginal)
        assertEquals(homeViewModel.backSearchMode, false)


    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}