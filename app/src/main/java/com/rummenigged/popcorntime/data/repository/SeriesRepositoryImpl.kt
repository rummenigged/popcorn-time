package com.rummenigged.popcorntime.data.repository

import com.rummenigged.popcorntime.data.remoteDataSource.SeriesRemoteDataSource
import com.rummenigged.popcorntime.domain.Series
import com.rummenigged.popcorntime.domain.SeriesRepository

class SeriesRepositoryImpl(
    private val seriesRemoteDataSource: SeriesRemoteDataSource
): SeriesRepository {
    override suspend fun getSeriesList(page: Int?): List<Series> =
        seriesRemoteDataSource.fetchSeriesList(page).map { series ->
            series.asSafe().asDomain()
        }
}