package com.example.myapplication.DI

import com.example.coin.network.ProductsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.jvm.java


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.wayfair.io/interview-sandbox/android/json-to-list/")
            .addConverterFactory(GsonConverterFactory.create()).build()

    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ProductsApi = retrofit.create(
        ProductsApi::class.java
    )


}

