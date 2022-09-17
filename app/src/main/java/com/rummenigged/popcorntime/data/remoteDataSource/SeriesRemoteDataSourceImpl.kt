package com.rummenigged.popcorntime.data.remoteDataSource

import com.rummenigged.popcorntime.common.NetworkException
import com.rummenigged.popcorntime.common.Outcome
import com.rummenigged.popcorntime.data.model.SeriesRaw
import com.rummenigged.popcorntime.data.network.api.SeriesApi
import com.rummenigged.popcorntime.data.network.utils.parseResponse
import javax.inject.Inject

class SeriesRemoteDataSourceImpl @Inject constructor(
    private val seriesApi: SeriesApi
): SeriesRemoteDataSource {
    override suspend fun fetchSeriesList(page: Int?): List<SeriesRaw> =
        when(val outcome = seriesApi.fetchSeriesList(page).parseResponse()){
            is Outcome.Success -> outcome.value
            is Outcome.Failure -> throw NetworkException.parse(outcome.statusCode)
        }
}