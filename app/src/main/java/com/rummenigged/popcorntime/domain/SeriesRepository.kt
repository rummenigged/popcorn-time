package com.rummenigged.popcorntime.domain

interface SeriesRepository {
//    fun getSeriesListPaging(): Flow<PagingData<Series>>

    suspend fun getSeriesList(page: Int? = null): List<Series>

    suspend fun getSeriesDetail(seriesId: Int): Series
}