package com.example.coin.usecase

interface DataUseCase {

    suspend fun  getData(): Result
}