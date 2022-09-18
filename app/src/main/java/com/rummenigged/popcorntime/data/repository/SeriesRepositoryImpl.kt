package com.rummenigged.popcorntime.data.repository

import com.rummenigged.popcorntime.data.remoteDataSource.SeriesRemoteDataSource
import com.rummenigged.popcorntime.domain.model.Episode
import com.rummenigged.popcorntime.domain.model.Season
import com.rummenigged.popcorntime.domain.model.Series
import com.rummenigged.popcorntime.domain.model.SeriesSearchResult
import com.rummenigged.popcorntime.domain.repository.SeriesRepository
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val seriesRemoteDataSource: SeriesRemoteDataSource
): SeriesRepository {

    override suspend fun getSeriesList(page: Int): List<Series> =
        seriesRemoteDataSource.fetchSeriesList(page ?: 0).map { series ->
            series.asSafe().asDomain()
        }

    override suspend fun searchSeries(query: String): List<SeriesSearchResult> =
        seriesRemoteDataSource.searchSeriesList(query).map { series ->
            series.asSafe().asDomain()
        }

    override suspend fun getSeriesDetail(seriesId: Int): Series =
        seriesRemoteDataSource.fetchSeriesDetails(seriesId)
            .asSafe().asDomain()

    override suspend fun getSeriesSeasons(seriesId: Int): List<Season> =
        seriesRemoteDataSource.fetchSeriesSeasons(seriesId).map {
            it.asSafe()
                .asDomain()
        }

    override suspend fun getEpisodes(seasonId: Int): List<Episode> =
        seriesRemoteDataSource.fetchSeasonEpisodes(seasonId).map {
            it.asSafe().asDomain()
        }

    override suspend fun getEpisodeDetails(url: String): Episode =
        seriesRemoteDataSource.fetchEpisodeDetail(url)
            .asSafe()
            .asDomain()
}