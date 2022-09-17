package com.rummenigged.popcorntime.data.remoteDataSource

import com.rummenigged.popcorntime.data.model.EpisodeRaw
import com.rummenigged.popcorntime.data.model.SeasonRaw
import com.rummenigged.popcorntime.data.model.SeriesRaw

interface SeriesRemoteDataSource {
   suspend fun fetchSeriesList(page: Int = 0): List<SeriesRaw>

   suspend fun fetchSeriesDetails(seriesId: Int): SeriesRaw

   suspend fun fetchSeriesSeasons(seriesId: Int): List<SeasonRaw>

   suspend fun fetchSeasonEpisodes(seasonId: Int): List<EpisodeRaw>
}