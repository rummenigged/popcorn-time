package com.rummenigged.popcorntime.di.remoteDataSourceModule

import com.rummenigged.popcorntime.data.remoteDataSource.SeriesRemoteDataSource
import com.rummenigged.popcorntime.data.remoteDataSource.SeriesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun provideSeriesRemoteDataSource(seriesRemoteDataSource: SeriesRemoteDataSourceImpl)
    : SeriesRemoteDataSource
}