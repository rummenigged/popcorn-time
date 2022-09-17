package com.rummenigged.popcorntime.apiUnitTest

import com.rummenigged.popcorntime.data.network.api.SeriesApi
import com.rummenigged.popcorntime.utils.getRawResource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class SeriesApiUnitTest {

    private val seriesId = 1
    private val seasonId = 2

    @Test
    fun `assert fetch series list success`() = runTest {
        val mockServer = MockWebServer()
        mockServer.start()

        mockServer.enqueue(MockResponse().setBody(getRawResource("series_list.json")))

        val retrofit = Retrofit.Builder()
            .baseUrl(mockServer.url(""))
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()))
            .build()

        val service = retrofit.create(SeriesApi::class.java)
        val result = service.fetchSeriesList()

        val request  = mockServer.takeRequest()
        mockServer.shutdown()

        assert(request.method == "GET")
        assert(request.path == "/shows")
        assert(result.body()?.size == 10)
    }

    @Test
    fun `assert fetch series list with page success`() = runTest {
        val mockServer = MockWebServer()
        mockServer.start()

        mockServer.enqueue(MockResponse().setBody(getRawResource("series_list.json")))

        val retrofit = Retrofit.Builder()
            .baseUrl(mockServer.url(""))
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()))
            .build()

        val page = 1
        val service = retrofit.create(SeriesApi::class.java)
        val result = service.fetchSeriesList(page)

        val request  = mockServer.takeRequest()
        mockServer.shutdown()

        assert(request.method == "GET")
        assert(request.path == "/shows?page=$page")
        assert(result.body()?.size == 10)
    }

    @Test
    fun `assert fetch series details success`() = runTest {
        val mockServer = MockWebServer()
        mockServer.start()

        mockServer.enqueue(MockResponse().setBody(getRawResource("series_details.json")))

        val retrofit = Retrofit.Builder()
            .baseUrl(mockServer.url(""))
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()))
            .build()

        val service = retrofit.create(SeriesApi::class.java)
        val result = service.fetchSeriesDetail(seriesId)

        val request  = mockServer.takeRequest()
        mockServer.shutdown()

        assert(request.method == "GET")
        assert(request.path == "/shows/$seriesId")
        assert(result.body()?.id == seriesId)
    }

    @Test
    fun `assert fetch series season list success`() = runTest {
        val mockServer = MockWebServer()
        mockServer.start()

        mockServer.enqueue(MockResponse().setBody(getRawResource("season_list.json")))

        val retrofit = Retrofit.Builder()
            .baseUrl(mockServer.url(""))
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()))
            .build()

        val service = retrofit.create(SeriesApi::class.java)
        val result = service.fetchSeriesSeason(seriesId)

        val request  = mockServer.takeRequest()
        mockServer.shutdown()

        assert(request.method == "GET")
        assert(request.path == "/shows/$seriesId/seasons")
        assert(result.body()?.size == 3)
    }

    @Test
    fun `assert fetch season episodes list success`() = runTest {
        val mockServer = MockWebServer()
        mockServer.start()

        mockServer.enqueue(MockResponse().setBody(getRawResource("episodes_list.json")))

        val retrofit = Retrofit.Builder()
            .baseUrl(mockServer.url(""))
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()))
            .build()

        val service = retrofit.create(SeriesApi::class.java)
        val result = service.fetchSeasonEpisodes(seasonId)

        val request  = mockServer.takeRequest()
        mockServer.shutdown()

        assert(request.method == "GET")
        assert(request.path == "/seasons/$seasonId/episodes")
        assert(result.body()?.size == 13)
    }
}