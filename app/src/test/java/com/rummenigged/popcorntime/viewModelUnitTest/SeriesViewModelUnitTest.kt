package com.rummenigged.popcorntime.viewModelUnitTest

import app.cash.turbine.test
import com.rummenigged.popcorntime.fakes.SeriesRemoteDataSourceFake
import com.rummenigged.popcorntime.fakes.SeriesRepositoryFake
import com.rummenigged.popcorntime.view.SeriesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SeriesViewModelUnitTest {

    private lateinit var seriesViewModelSuccess: SeriesViewModel
    private lateinit var seriesViewModelError: SeriesViewModel

    private val coroutineDispatcher = StandardTestDispatcher()

    private val listSize = 4
    private val seriesId = 8
    private val errorCode = 404
    private val errorMessage = "Resource Not Found"

    @Before
    fun setup(){
        Dispatchers.setMain(coroutineDispatcher)
        seriesViewModelSuccess = SeriesViewModel(
            SeriesRepositoryFake(
                seriesList = SeriesRepositoryFake.createSeriesListFakeList(listSize),
                seriesDetail = SeriesRemoteDataSourceFake.getMockSeriesDetails(seriesId)
                    .asSafe()
                    .asDomain()
            )
        )

        seriesViewModelError = SeriesViewModel(
            SeriesRepositoryFake(
                hasError = true,
                errorCode = 404,
                errorMessage = errorMessage
            )
        )
    }

    @After
    fun release(){
        Dispatchers.resetMain()
    }

    @Test
    fun `assert get series list success `() = runTest {
        seriesViewModelSuccess.getSeriesList()
        seriesViewModelSuccess.seriesUiState.test {
            awaitItem().also {
                assert(it.isLoading)
                assert(it.errorMessage.isNullOrEmpty())
            }
            awaitItem().also {
                assert(it.seriesList?.size == listSize)
                assert(!it.isLoading)
                assert(it.errorMessage.isNullOrEmpty())
            }
        }
    }

    @Test
    fun `assert get series list error `() = runTest {
        seriesViewModelError.getSeriesList()
        seriesViewModelError.seriesUiState.test {
            awaitItem().also {
                assert(it.isLoading)
                assert(it.errorMessage.isNullOrEmpty())
            }
            awaitItem().also {
                println(it.errorMessage)
                assert(!it.isLoading)
                assert(it.errorMessage == errorMessage)
                assert(it.seriesList.isNullOrEmpty())
            }
        }
    }
}