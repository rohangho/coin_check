package com.example.coin.network

import com.example.example.Products
import retrofit2.Response
import retrofit2.http.GET

interface ProductsApi {

    @GET("products.v1.json")
    suspend fun getProductList(): Response<Array<Products>>

}