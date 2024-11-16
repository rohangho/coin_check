package com.example.coin.di

import com.example.coin.network.CoinApi
import com.example.coin.usecase.CoinNetworkUseCase
import com.example.coin.usecase.DataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideUseCase(coinApi: CoinApi): DataUseCase {
        return CoinNetworkUseCase(coinApi)
    }

}