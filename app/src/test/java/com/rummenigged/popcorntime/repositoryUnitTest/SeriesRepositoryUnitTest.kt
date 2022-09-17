package com.rummenigged.popcorntime.repositoryUnitTest

import com.rummenigged.popcorntime.data.repository.SeriesRepositoryImpl
import com.rummenigged.popcorntime.domain.SeriesRepository
import com.rummenigged.popcorntime.fakes.SeriesRemoteDataSourceFake
import com.rummenigged.popcorntime.fakes.SeriesRepositoryFake
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SeriesRepositoryUnitTest {

    private lateinit var seriesRepository: SeriesRepository

    private val listSize = 4
    private val seasonListSize = 6
    private val episodesListSize = 20
    private val seriesId = 7
    private val seasonId = 2

    @Before
    fun setup(){
        seriesRepository = SeriesRepositoryImpl(
            seriesRemoteDataSource = SeriesRemoteDataSourceFake(
                seriesList = SeriesRemoteDataSourceFake.createSeriesListFakeList(listSize),
                seriesDetail = SeriesRemoteDataSourceFake.getMockSeriesDetails(seriesId),
                seasonList = SeriesRemoteDataSourceFake.createSeriesSeasonListFakeList(seasonListSize),
                episodesList = SeriesRemoteDataSourceFake.createSeasonEpisodesListFakeList(episodesListSize)
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
    fun `assert get series details success `() = runTest {
        seriesRepository.getSeriesDetail(seriesId).also { result ->
            assert(result.id == seriesId)
        }
    }

    @Test
    fun `assert get series season success`() = runTest {
        seriesRepository.getSeriesSeasons(seriesId).also { result ->
            assert(result.size == seasonListSize)
        }
    }

    @Test
    fun `assert get season episodes success`() = runTest {
        seriesRepository.getEpisodes(seasonId).also { result ->
            assert(result.size == episodesListSize)
        }
    }
}