package com.rummenigged.popcorntime.data.network.api

import com.rummenigged.popcorntime.data.model.SeriesRaw
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesApi {

    @GET("/shows")
    suspend fun fetchSeriesList(
        @Query("page") page: Int? = null
    ): Response<List<SeriesRaw>>
}