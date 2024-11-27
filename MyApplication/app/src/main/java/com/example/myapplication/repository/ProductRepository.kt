package com.example.coin.repository

import com.example.coin.usecase.DataUseCase
import com.example.coin.usecase.Result
import com.example.coin.usecase.Result.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productsNetworkUseCase: DataUseCase) {
    fun getData() = flow<Result> {
        val body = productsNetworkUseCase.getData()
        when (body) {
            ApiError -> {
                emit(ApiError)
            }

            is ApiSuccess -> {
                emit(ApiSuccess(body.data))
            }
        }
    }
}