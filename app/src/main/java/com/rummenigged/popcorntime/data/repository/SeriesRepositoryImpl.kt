package com.rummenigged.popcorntime.data.repository

import com.rummenigged.popcorntime.data.remoteDataSource.SeriesRemoteDataSource
import com.rummenigged.popcorntime.domain.Series
import com.rummenigged.popcorntime.domain.SeriesRepository
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val seriesRemoteDataSource: SeriesRemoteDataSource
): SeriesRepository {
//    override fun getSeriesListPaging(): Flow<PagingData<Series>> =
//        Pager(
//            config = PagingConfig(
//                pageSize = 200,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { SeriesPagingSource(seriesRemoteDataSource)}
//        ).flow

    override suspend fun getSeriesList(page: Int?): List<Series> =
        seriesRemoteDataSource.fetchSeriesList(page ?: 0).map { series ->
            series.asSafe().asDomain()
        }

    override suspend fun getSeriesDetail(seriesId: Int): Series =
        seriesRemoteDataSource.fetchSeriesDetails(seriesId)
            .asSafe().asDomain()
}