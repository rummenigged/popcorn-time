package com.rummenigged.popcorntime.data.remoteDataSource

import com.rummenigged.popcorntime.common.NetworkException
import com.rummenigged.popcorntime.common.Outcome
import com.rummenigged.popcorntime.data.model.raw.EpisodeRaw
import com.rummenigged.popcorntime.data.model.raw.SeasonRaw
import com.rummenigged.popcorntime.data.model.raw.SeriesRaw
import com.rummenigged.popcorntime.data.model.raw.SeriesSearchResultRaw
import com.rummenigged.popcorntime.data.network.api.SeriesApi
import com.rummenigged.popcorntime.data.network.utils.parseResponse
import javax.inject.Inject

class SeriesRemoteDataSourceImpl @Inject constructor(
    private val seriesApi: SeriesApi
): SeriesRemoteDataSource {
    override suspend fun fetchSeriesList(page: Int): List<SeriesRaw> =
        when(val outcome = seriesApi.fetchSeriesList(page).parseResponse()){
            is Outcome.Success -> outcome.value
            is Outcome.Failure -> throw NetworkException.parse(outcome.statusCode)
        }

    override suspend fun searchSeriesList(query: String): List<SeriesSearchResultRaw> =
        when(val outcome = seriesApi.searchSeriesList(query).parseResponse()){
            is Outcome.Success -> outcome.value
            is Outcome.Failure -> throw NetworkException.parse(outcome.statusCode)
        }

    override suspend fun fetchSeriesDetails(seriesId: Int): SeriesRaw =
        when(val outcome = seriesApi.fetchSeriesDetail(seriesId).parseResponse()){
            is Outcome.Success -> outcome.value
            is Outcome.Failure -> throw NetworkException.parse(outcome.statusCode)
        }

    override suspend fun fetchSeriesSeasons(seriesId: Int): List<SeasonRaw> =
        when(val outcome = seriesApi.fetchSeriesSeason(seriesId).parseResponse()){
            is Outcome.Success -> outcome.value
            is Outcome.Failure -> throw NetworkException.parse(outcome.statusCode)
        }

    override suspend fun fetchSeasonEpisodes(seasonId: Int): List<EpisodeRaw> =
        when(val outcome = seriesApi.fetchSeasonEpisodes(seasonId).parseResponse()){
            is Outcome.Success -> outcome.value
            is Outcome.Failure -> throw NetworkException.parse(outcome.statusCode)
        }

    override suspend fun fetchEpisodeDetail(url: String): EpisodeRaw =
        when(val outcome = seriesApi.fetchEpisodeDetails(url).parseResponse()){
            is Outcome.Success -> outcome.value
            is Outcome.Failure -> throw NetworkException.parse(outcome.statusCode)
        }
}