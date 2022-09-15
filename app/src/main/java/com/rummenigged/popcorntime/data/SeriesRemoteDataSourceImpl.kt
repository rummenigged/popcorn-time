package com.rummenigged.popcorntime.data

import com.rummenigged.popcorntime.common.NetworkException
import com.rummenigged.popcorntime.common.Outcome

class SeriesRemoteDataSourceImpl(
    private val seriesApi: SeriesApi
): SeriesRemoteDataSource {
    override suspend fun fetchSeriesList(page: Int?): List<SeriesRaw> =
        when(val outcome = seriesApi.fetchSeriesList(page).parseResponse()){
            is Outcome.Success -> outcome.value
            is Outcome.Failure -> throw NetworkException.parse(outcome.statusCode)
        }
}