package com.example.myapplication.DI

import com.example.coin.network.ProductsApi
import com.example.coin.usecase.DataUseCase
import com.example.coin.usecase.ProductsNetworkUseCase
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
    fun provideUseCase(productsApi: ProductsApi): DataUseCase {
        return ProductsNetworkUseCase(productsApi)
    }

}