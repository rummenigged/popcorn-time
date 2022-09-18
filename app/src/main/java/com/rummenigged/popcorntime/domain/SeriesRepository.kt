package com.rummenigged.popcorntime.domain

interface SeriesRepository {

    suspend fun getSeriesList(page: Int): List<Series>

    suspend fun searchSeries(query: String): List<SeriesSearchResult>

    suspend fun getSeriesDetail(seriesId: Int): Series

    suspend fun getSeriesSeasons(seriesId: Int): List<Season>

    suspend fun getEpisodes(seasonId: Int): List<Episode>

    suspend fun getEpisodeDetails(url:String): Episode
}