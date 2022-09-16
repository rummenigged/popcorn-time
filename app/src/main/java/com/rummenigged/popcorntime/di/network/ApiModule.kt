package com.rummenigged.popcorntime.di.network

import com.rummenigged.popcorntime.common.RetrofitBuilder
import com.rummenigged.popcorntime.data.network.api.SeriesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    @Provides
    fun provideSeriesApi(): SeriesApi =
        RetrofitBuilder()
            .baseUrl("https://api.tvmaze.com")
            .build()
            .create(SeriesApi::class.java)

}