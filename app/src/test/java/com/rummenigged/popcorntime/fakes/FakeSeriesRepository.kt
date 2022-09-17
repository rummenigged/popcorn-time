package com.rummenigged.popcorntime.fakes

import com.rummenigged.popcorntime.common.NetworkException
import com.rummenigged.popcorntime.domain.Season
import com.rummenigged.popcorntime.domain.Series
import com.rummenigged.popcorntime.domain.SeriesRepository

class SeriesRepositoryFake(
   private val seriesList: List<Series> = emptyList(),
   private val seriesDetail: Series? = null,
   private val seasonSeriesList: List<Season> = emptyList(),
   private val hasError: Boolean = false,
   private val errorCode: Int = 400,
   private val errorMessage: String = "",
): SeriesRepository {

    override suspend fun getSeriesList(page: Int?): List<Series> = if (hasError){
        throw NetworkException.parse(errorCode, errorMessage)
    }else{
        seriesList
    }

    override suspend fun getSeriesDetail(seriesId: Int): Series = if (hasError){
        throw NetworkException.parse(errorCode, errorMessage)
    }else{
        seriesDetail ?: SeriesRemoteDataSourceFake.getMockSeriesDetails(0)
            .asSafe().asDomain()
    }


    override suspend fun getSeriesSeasons(seriesId: Int): List<Season> = if (hasError){
        throw NetworkException.parse(errorCode, errorMessage)
    }else{
        seasonSeriesList
    }

    companion object{
        fun createSeriesListFakeList(amount: Int): List<Series> =
            SeriesRemoteDataSourceFake.createSeriesListFakeList(amount)
                .map { it.asSafe().asDomain() }
    }
}