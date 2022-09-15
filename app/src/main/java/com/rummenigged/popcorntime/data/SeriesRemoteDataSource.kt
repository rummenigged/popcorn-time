package com.rummenigged.popcorntime.data

interface SeriesRemoteDataSource {
   suspend fun fetchSeriesList(page: Int?): List<SeriesRaw>
}