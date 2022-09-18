package com.rummenigged.popcorntime.di.repository

import com.rummenigged.popcorntime.data.repository.SeriesRepositoryImpl
import com.rummenigged.popcorntime.domain.repository.SeriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun provideSeriesRepository(seriesRepository: SeriesRepositoryImpl): SeriesRepository
}