package com.example.coin.usecase

import com.example.coin.model.BaseCoinModel
import com.example.coin.network.CoinApi
import javax.inject.Inject

class CoinApiUseCase @Inject constructor(val coinApi: CoinApi) {

    suspend fun getData(): Result {
       val data  =  coinApi.getCoinList()
        return if(data.isSuccessful)
            Result.ApiSuccess(data.body()?:emptyArray())
        else
            (Result.ApiError)

    }
}




sealed class Result()
{
    data class ApiSuccess(val data: Array<BaseCoinModel>) : Result()
    object ApiError : Result()
}