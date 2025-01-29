package com.example.coin.usecase

import kotlinx.coroutines.flow.Flow

interface DataUseCase {

    suspend fun  getData(): Result
}