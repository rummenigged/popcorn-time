package com.rummenigged.popcorntime.remoteDataSourceUnitTest

import com.rummenigged.popcorntime.common.NetworkException
import com.rummenigged.popcorntime.data.remoteDataSource.SeriesRemoteDataSource
import com.rummenigged.popcorntime.data.remoteDataSource.SeriesRemoteDataSourceImpl
import com.rummenigged.popcorntime.fakes.SeriesApiFake
import com.rummenigged.popcorntime.fakes.SeriesRemoteDataSourceFake
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SeriesRemoteDataSourceUnitTest {

    private lateinit var seriesRemoteDataSourceSuccess: SeriesRemoteDataSource
    private lateinit var seriesRemoteDataSourceError: SeriesRemoteDataSource

    private val listSize = 7
    private val seasonListSize = 3
    private val episodesListSize = 10
    private val seriesId = 1
    private val seasonId = 20

    @Before
    fun setup(){
        seriesRemoteDataSourceSuccess = SeriesRemoteDataSourceImpl(
            SeriesApiFake.createSuccessResponseApi(
                SeriesRemoteDataSourceFake.createSeriesListFakeList(listSize),
                SeriesRemoteDataSourceFake.getMockSeriesDetails(seriesId),
                SeriesRemoteDataSourceFake.createSeriesSeasonListFakeList(seasonListSize),
                SeriesRemoteDataSourceFake.createSeasonEpisodesListFakeList(episodesListSize)
            )
        )

        seriesRemoteDataSourceError = SeriesRemoteDataSourceImpl(
            SeriesApiFake.createErrorResponseApi(
                errorCode = 400,
                errorMessage = "Error test message"
            )
        )
    }

    @Test
    fun `assert fetch series list success`() = runTest {
        seriesRemoteDataSourceSuccess.fetchSeriesList(1).also { result ->
            assert(result.size == listSize)
        }
    }

    @Test
    fun `assert fetch series list handler bad request error`() = runTest {
        try {
            seriesRemoteDataSourceError.fetchSeriesList(1)
        }catch (e: NetworkException){
            assert(e is NetworkException.BadRequest)
        }
    }

    @Test
    fun `assert fetch series list handler not found error`() = runTest {
        val serverError = SeriesRemoteDataSourceImpl(
            SeriesApiFake.createErrorResponseApi(
                errorCode = 404,
                errorMessage = "Error test message"
            )
        )
        try {
            serverError.fetchSeriesList(1)
        }catch (e: NetworkException){
            assert(e is NetworkException.NotFound)
        }
    }

    @Test
    fun `assert fetch series list handler Forbidden error`() = runTest {
        val serverError = SeriesRemoteDataSourceImpl(
            SeriesApiFake.createErrorResponseApi(
                errorCode = 403,
                errorMessage = "Error test message"
            )
        )
        try {
            serverError.fetchSeriesList(1)
        }catch (e: NetworkException){
            assert(e is NetworkException.Forbidden)
        }
    }

    @Test
    fun `assert fetch series list handler others error`() = runTest {
        val serverError = SeriesRemoteDataSourceImpl(
            SeriesApiFake.createErrorResponseApi(
                errorCode = 418,
                errorMessage = "Error test message"
            )
        )
        try {
            serverError.fetchSeriesList(1)
        }catch (e: NetworkException){
            assert(e is NetworkException.InternalError)
        }
    }

    @Test
    fun `assert fetch series details success`() = runTest {
        seriesRemoteDataSourceSuccess.fetchSeriesDetails(seriesId).also { result ->
            assert(result.id == seriesId)
        }
    }

    @Test
    fun `assert fetch series seasons success`() = runTest {
        seriesRemoteDataSourceSuccess.fetchSeriesSeasons(seriesId).also { result ->
            assert(result.size == seasonListSize)
        }
    }

    @Test
    fun `assert fetch season episodes success`() = runTest {
        seriesRemoteDataSourceSuccess.fetchSeasonEpisodes(seasonId).also { result ->
            assert(result.size == episodesListSize)
        }
    }
}