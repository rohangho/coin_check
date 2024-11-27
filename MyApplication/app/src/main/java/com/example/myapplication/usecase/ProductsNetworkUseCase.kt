package com.example.coin.usecase

import com.example.coin.network.ProductsApi
import com.example.example.Products
import javax.inject.Inject

class ProductsNetworkUseCase @Inject constructor(val productApi: ProductsApi) : DataUseCase {

    override suspend fun getData(): Result {
        return try {
            val data = productApi.getProductList()
            return if (data.isSuccessful)
                Result.ApiSuccess(data.body() ?: emptyArray())
            else
                Result.ApiError
        } catch (e: Exception) {
            Result.ApiError
        }

    }
}


sealed class Result() {
    data class ApiSuccess(val data: Array<Products>) : Result()
    object ApiError : Result()
}