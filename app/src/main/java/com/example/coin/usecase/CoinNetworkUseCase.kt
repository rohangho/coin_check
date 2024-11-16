package com.example.coin.usecase

import com.example.coin.model.BaseCoinModel
import com.example.coin.network.CoinApi
import javax.inject.Inject

class CoinNetworkUseCase @Inject constructor(val coinApi: CoinApi): DataUseCase {

    override suspend fun getData(): Result {
        return try {
            val data = coinApi.getCoinList()
            return if (data.isSuccessful)
                Result.ApiSuccess(data.body() ?: emptyArray())
            else
                Result.ApiError
        } catch (e: Exception) {
            Result.ApiError
        }

    }
}




sealed class Result()
{
    data class ApiSuccess(val data: Array<BaseCoinModel>) : Result()
    object ApiError : Result()
}