package com.example.coin.repository

import android.util.Log
import com.example.coin.usecase.DataUseCase
import com.example.coin.usecase.Result
import com.example.coin.usecase.Result.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.w3c.dom.Text
import javax.inject.Inject
import kotlin.math.log

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








    fun getUpdatedText(): Flow<String>
    {
       return flow<String> {while (true) {
           Log.e("dfgh", "getUpdatedText: ", )
                emit(System.currentTimeMillis().toString())
                delay(1000)
            }
        }
    }


}
