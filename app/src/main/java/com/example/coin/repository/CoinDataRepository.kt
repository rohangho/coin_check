package com.example.coin.repository

import com.example.coin.usecase.CoinNetworkUseCase
import com.example.coin.usecase.DataUseCase
import com.example.coin.usecase.Result
import com.example.coin.usecase.Result.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinDataRepository @Inject constructor(private val coinNetworkUseCase: DataUseCase){
     fun getData() = flow<Result> {
        val body = coinNetworkUseCase.getData()
        when(body) {
            ApiError -> {
                emit(ApiError)
            }
            is ApiSuccess -> {
               emit(ApiSuccess(body.data))
            }
    }
    }
}