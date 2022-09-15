package com.rummenigged.popcorntime.domain

interface SeriesRepository {
    suspend fun getSeriesList(page: Int? = null): List<Series>
}