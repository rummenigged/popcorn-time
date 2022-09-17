package com.rummenigged.popcorntime.fakes

import com.rummenigged.popcorntime.common.NetworkException
import com.rummenigged.popcorntime.data.model.SeriesRaw
import com.rummenigged.popcorntime.domain.Series
import com.rummenigged.popcorntime.domain.SeriesRepository
import kotlin.jvm.Throws

class SeriesRepositoryFake(
   private val seriesList: List<Series> = emptyList(),
   private val hasError: Boolean = false,
   private val errorCode: Int = 400,
   private val errorMessage: String = "",
): SeriesRepository {
    override suspend fun getSeriesList(page: Int?): List<Series> = if (hasError){
        throw NetworkException.parse(errorCode, errorMessage)
    }else{
        seriesList
    }

    companion object{
        fun createSeriesListFakeList(amount: Int): List<Series> =
            SeriesRemoteDataSourceFake.createSeriesListFakeList(amount)
                .map { it.asSafe().asDomain() }
    }
}