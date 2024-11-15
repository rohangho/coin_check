package com.example.coin.di

import com.example.coin.network.CoinApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://37656be98b8f42ae8348e4da3ee3193f")
            .addConverterFactory(
                Json.asConverterFactory(MediaType.get("application/json"))
            ).build()

    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): CoinApi= retrofit.create(CoinApi::class.java)




}