package com.example.coin.network

import com.example.coin.model.BaseCoinModel
import retrofit2.Response
import retrofit2.http.GET

interface CoinApi {

    @GET(".api.mockbin.io/")
    suspend fun getCoinList(): Response<Array<BaseCoinModel>>

}