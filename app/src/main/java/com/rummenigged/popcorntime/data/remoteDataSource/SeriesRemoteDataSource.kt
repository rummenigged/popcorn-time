package com.rummenigged.popcorntime.data.remoteDataSource

import com.rummenigged.popcorntime.data.model.SeriesRaw

interface SeriesRemoteDataSource {
   suspend fun fetchSeriesList(page: Int? = null): List<SeriesRaw>
}