package com.rummenigged.popcorntime.data.remoteDataSource

import com.rummenigged.popcorntime.data.model.EpisodeRaw
import com.rummenigged.popcorntime.data.model.SeasonRaw
import com.rummenigged.popcorntime.data.model.SeriesRaw
import com.rummenigged.popcorntime.data.model.SeriesSearchResultRaw

interface SeriesRemoteDataSource {
   suspend fun fetchSeriesList(page: Int = 0): List<SeriesRaw>

   suspend fun searchSeriesList(query: String): List<SeriesSearchResultRaw>

   suspend fun fetchSeriesDetails(seriesId: Int): SeriesRaw

   suspend fun fetchSeriesSeasons(seriesId: Int): List<SeasonRaw>

   suspend fun fetchSeasonEpisodes(seasonId: Int): List<EpisodeRaw>

   suspend fun fetchEpisodeDetail(url: String): EpisodeRaw
}