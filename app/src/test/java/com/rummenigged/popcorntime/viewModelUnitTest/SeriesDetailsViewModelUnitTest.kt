package com.rummenigged.popcorntime.viewModelUnitTest

import app.cash.turbine.test
import com.rummenigged.popcorntime.fakes.SeriesRemoteDataSourceFake
import com.rummenigged.popcorntime.fakes.SeriesRepositoryFake
import com.rummenigged.popcorntime.view.SeriesDetailsViewModel
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
class SeriesDetailsViewModelUnitTest {

    private lateinit var seriesDetailsViewModelSuccess: SeriesDetailsViewModel
    private lateinit var seriesDetailViewModelError: SeriesDetailsViewModel

    private val coroutineDispatcher = StandardTestDispatcher()

    private val listSize = 4
    private val seasonListSize = 8
    private val seriesId = 8
    private val errorCode = 404
    private val errorMessage = "Resource Not Found"

    @Before
    fun setup(){
        Dispatchers.setMain(coroutineDispatcher)
        seriesDetailsViewModelSuccess = SeriesDetailsViewModel(
            SeriesRepositoryFake(
                seriesList = SeriesRepositoryFake.createSeriesListFakeList(listSize),
                seriesDetail = SeriesRemoteDataSourceFake.getMockSeriesDetails(seriesId)
                    .asSafe()
                    .asDomain(),
                seasonSeriesList = SeriesRemoteDataSourceFake
                    .createSeriesSeasonListFakeList(seasonListSize).map {
                        it.asSafe().asDomain()
                    }
            )
        )

        seriesDetailViewModelError = SeriesDetailsViewModel(
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
    fun `assert get series details success `() = runTest {
        seriesDetailsViewModelSuccess.getSeriesDetails(seriesId)
        seriesDetailsViewModelSuccess.seriesDetailsUiState.test {
            awaitItem().also {
                assert(it.isLoading)
                assert(it.errorMessage.isNullOrEmpty())
            }
            awaitItem().also {
                assert(it.seriesDetails?.name?.isNotEmpty() ?: false)
                assert(it.seriesDetails?.genres?.isNotEmpty() ?: false)
                assert(it.seriesDetails?.posterUrl?.isNotEmpty() ?: false)
                assert(it.seriesDetails?.summary?.isNotEmpty() ?: false)
                assert(!it.isLoading)
                assert(it.errorMessage.isNullOrEmpty())
            }
        }
    }

    @Test
    fun `assert get series details error `() = runTest {
        seriesDetailViewModelError.getSeriesDetails(seriesId)
        seriesDetailViewModelError.seriesDetailsUiState.test {
            awaitItem().also {
                assert(it.isLoading)
                assert(it.errorMessage.isNullOrEmpty())
            }
            awaitItem().also {
                println(it.errorMessage)
                assert(!it.isLoading)
                assert(it.errorMessage == errorMessage)
                assert(it.seriesDetails == null)
            }
        }
    }

    @Test
    fun `assert get series season success `() = runTest {
        seriesDetailsViewModelSuccess.getSeriesSeason(seriesId)
        seriesDetailsViewModelSuccess.seriesDetailsUiState.test {
            awaitItem().also {
                assert(it.isLoading)
                assert(it.seasonList.isNullOrEmpty())
                assert(it.errorMessage.isNullOrEmpty())
            }
            awaitItem().also {
                assert(it.seasonList?.size == seasonListSize)
                assert(!it.isLoading)
                assert(it.errorMessage.isNullOrEmpty())
            }
        }
    }

    @Test
    fun `assert get series list error `() = runTest {
        seriesDetailViewModelError.getSeriesDetails(seriesId)
        seriesDetailViewModelError.seriesDetailsUiState.test {
            awaitItem().also {
                assert(it.isLoading)
                assert(it.errorMessage.isNullOrEmpty())
            }
            awaitItem().also {
                println(it.errorMessage)
                assert(!it.isLoading)
                assert(it.errorMessage == errorMessage)
                assert(it.seasonList.isNullOrEmpty())
            }
        }
    }
}