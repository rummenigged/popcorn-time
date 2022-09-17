package com.rummenigged.popcorntime.domain

interface SeriesRepository {

    suspend fun getSeriesList(page: Int? = null): List<Series>

    suspend fun getSeriesDetail(seriesId: Int): Series

    suspend fun getSeriesSeasons(seriesId: Int): List<Season>

    suspend fun getEpisodes(seasonId: Int): List<Episode>
}