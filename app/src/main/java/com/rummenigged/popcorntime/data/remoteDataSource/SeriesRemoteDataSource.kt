package com.rummenigged.popcorntime.data.remoteDataSource

import com.rummenigged.popcorntime.data.model.SeasonRaw
import com.rummenigged.popcorntime.data.model.SeriesRaw
import retrofit2.Response

interface SeriesRemoteDataSource {
   suspend fun fetchSeriesList(page: Int = 0): List<SeriesRaw>

   suspend fun fetchSeriesDetails(seriesId: Int): SeriesRaw

   suspend fun fetchSeriesSeasons(seriesId: Int): List<SeasonRaw>
}