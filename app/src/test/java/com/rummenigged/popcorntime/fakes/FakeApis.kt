package com.rummenigged.popcorntime.fakes

import com.rummenigged.popcorntime.data.model.raw.EpisodeRaw
import com.rummenigged.popcorntime.data.model.raw.SeasonRaw
import com.rummenigged.popcorntime.data.network.api.SeriesApi
import com.rummenigged.popcorntime.data.model.raw.SeriesRaw
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class SeriesApiFake{

    companion object {
        fun createSuccessResponseApi(
            seriesList: List<SeriesRaw>,
            seriesDetail: SeriesRaw? = null,
            seriesSeasonList: List<SeasonRaw>? = null,
            seasonEpisodesList: List<EpisodeRaw>? = null
        ): SeriesApi = object : SeriesApi {
            override suspend fun fetchSeriesList(page: Int?): Response<List<SeriesRaw>> =
                Response.success(seriesList)

            override suspend fun fetchSeriesDetail(seriesId: Int): Response<SeriesRaw> =
                Response.success(seriesDetail)

            override suspend fun fetchSeriesSeason(seriesId: Int): Response<List<SeasonRaw>> =
                Response.success(seriesSeasonList)

            override suspend fun fetchSeasonEpisodes(seasonId: Int): Response<List<EpisodeRaw>> =
                Response.success(seasonEpisodesList)
        }

        fun createErrorResponseApi(
            errorCode: Int = 400,
            errorMessage: String = ""
        ): SeriesApi = object : SeriesApi {
            override suspend fun fetchSeriesList(page: Int?): Response<List<SeriesRaw>> =
                Response.error(errorCode, errorMessage.toResponseBody())

            override suspend fun fetchSeriesDetail(seriesId: Int): Response<SeriesRaw> =
                Response.error(errorCode, errorMessage.toResponseBody())

            override suspend fun fetchSeriesSeason(seriesId: Int): Response<List<SeasonRaw>> =
                Response.error(errorCode, errorMessage.toResponseBody())

            override suspend fun fetchSeasonEpisodes(seasonId: Int): Response<List<EpisodeRaw>> =
                Response.error(errorCode, errorMessage.toResponseBody())

        }
    }
}