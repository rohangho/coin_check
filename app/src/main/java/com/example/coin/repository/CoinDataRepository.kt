package com.example.coin.repository

import android.util.Log
import com.example.coin.usecase.CoinApiUseCase
import com.example.coin.usecase.Result
import javax.inject.Inject

class CoinDataRepository @Inject constructor(private val coinApiUseCase: CoinApiUseCase){



    suspend fun getData()
    {
        when(coinApiUseCase.getData()) {
            Result.ApiError -> {
                Log.e("Hiiiii", "getData: failure ", )
            }
            is Result.ApiSuccess -> {
                Log.e("Hiiiii", "getData: ", )
            }
        }
    }

}