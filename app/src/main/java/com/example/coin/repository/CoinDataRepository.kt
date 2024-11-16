package com.example.coin.repository

import android.util.Log
import com.example.coin.usecase.CoinApiUseCase
import com.example.coin.usecase.Result
import com.example.coin.usecase.Result.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinDataRepository @Inject constructor(private val coinApiUseCase: CoinApiUseCase){
     fun getData() = flow<Result> {
        val body = coinApiUseCase.getData()
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