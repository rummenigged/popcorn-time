package com.rummenigged.popcorntime.repositoryUnitTest

import com.rummenigged.popcorntime.data.repository.SeriesRepositoryImpl
import com.rummenigged.popcorntime.domain.SeriesRepository
import com.rummenigged.popcorntime.fakes.SeriesRemoteDataSourceFake
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SeriesRepositoryUnitTest {

    private lateinit var seriesRepository: SeriesRepository

    private val listSize = 4

    @Before
    fun setup(){
        seriesRepository = SeriesRepositoryImpl(
            seriesRemoteDataSource = SeriesRemoteDataSourceFake(
                seriesList = SeriesRemoteDataSourceFake.createSeriesListFakeList(listSize)
            )
        )
    }

    @Test
    fun `assert get series list success `() = runTest {
        seriesRepository.getSeriesList(1).also { result ->
            assert(result.size == listSize)
        }
    }

    @Test
    fun `assert get series list success without pass page parameter`() = runTest {
        seriesRepository.getSeriesList().also { result ->
            assert(result.size == listSize)
        }
    }
}