package com.rummenigged.popcorntime.data.network.api

import com.rummenigged.popcorntime.data.model.EpisodeRaw
import com.rummenigged.popcorntime.data.model.SeasonRaw
import com.rummenigged.popcorntime.data.model.SeriesRaw
import com.rummenigged.popcorntime.data.model.SeriesSearchResultRaw
import com.rummenigged.popcorntime.domain.SeriesSearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface SeriesApi {

    @GET("/shows")
    suspend fun fetchSeriesList(
        @Query("page") page: Int
    ): Response<List<SeriesRaw>>

    @GET("search/shows")
    suspend fun searchSeriesList(
        @Query("q") query: String
    ): Response<List<SeriesSearchResultRaw>>

    @GET("/shows/{series_id}")
    suspend fun fetchSeriesDetail(
        @Path("series_id") seriesId: Int
    ): Response<SeriesRaw>

    @GET("/shows/{series_id}/seasons")
    suspend fun fetchSeriesSeason(
        @Path("series_id") seriesId: Int
    ): Response<List<SeasonRaw>>

    @GET("/seasons/{season_id}/episodes")
    suspend fun fetchSeasonEpisodes(
        @Path("season_id") seasonId: Int
    ): Response<List<EpisodeRaw>>

    @GET
    suspend fun fetchEpisodeDetails(
        @Url url: String
    ): Response<EpisodeRaw>
}