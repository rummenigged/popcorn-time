package com.rummenigged.popcorntime.RemoteDataSourceUnitTest

import com.rummenigged.popcorntime.common.NetworkException
import com.rummenigged.popcorntime.data.remoteDataSource.SeriesRemoteDataSource
import com.rummenigged.popcorntime.data.remoteDataSource.SeriesRemoteDataSourceImpl
import com.rummenigged.popcorntime.fakes.SeriesApiFake
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SeriesRemoteDataSourceUnitTest {

    private lateinit var seriesRemoteDataSourceSuccess: SeriesRemoteDataSource
    private lateinit var seriesRemoteDataSourceError: SeriesRemoteDataSource

    private val listSize = 7

    @Before
    fun setup(){
        seriesRemoteDataSourceSuccess = SeriesRemoteDataSourceImpl(
            SeriesApiFake.createSuccessResponseApi(
                SeriesApiFake.createSeriesListFakeList(listSize)
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
}