package com.example.myapplication

import app.cash.turbine.test
import com.example.coin.repository.ProductRepository
import com.example.coin.viewmodel.HomeViewModel
import com.example.coin.viewmodel.ScreenState
import com.example.example.Products
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    private val productRepository: ProductRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var homeViewModel: HomeViewModel

    @Test
    fun `test getData success scenario`() = runTest {
        val mockData = emptyArray<Products>()
        every { (productRepository.getData())} returns(
            flow { emit(com.example.coin.usecase.Result.ApiSuccess(mockData)) }
        )

        homeViewModel = HomeViewModel(productRepository)

        homeViewModel.getData()

        homeViewModel.screenState.test {
            assertEquals(ScreenState.SuccessData(mockData), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test getData api error scenario`() = runTest {
        every {  (productRepository.getData())} returns (
            flow { emit(com.example.coin.usecase.Result.ApiError) }
        )

        homeViewModel = HomeViewModel(productRepository)

        homeViewModel.getData()

        homeViewModel.screenState.test {
            assertEquals(ScreenState.Error("Error"), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

}


@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}