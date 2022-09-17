package com.rummenigged.popcorntime.fakes

import com.rummenigged.popcorntime.data.model.SeriesRaw
import com.rummenigged.popcorntime.data.remoteDataSource.SeriesRemoteDataSource

class SeriesRemoteDataSourceFake(
    private val seriesList: List<SeriesRaw>?,
    private val seriesDetail: SeriesRaw? = null
): SeriesRemoteDataSource{

    override suspend fun fetchSeriesList(page: Int): List<SeriesRaw> =
        seriesList ?: emptyList()

    override suspend fun fetchSeriesDetails(seriesId: Int): SeriesRaw =
        seriesDetail ?: getMockSeriesDetails(0)

    companion object {

        fun getMockSeriesDetails(seriesId: Int): SeriesRaw =
            SeriesRaw(
                id = seriesId,
                url = "url_test_$seriesId",
                name = "Show $seriesId",
                type = "Tv Series",
                language = "English",
                genres = listOf(
                    "Comedy",
                    "Drama",
                    "Science"
                ),
                status = "Ended",
                runtime = 60,
                averageRuntime = 60,
                premiered = "011-11-06",
                ended = "2016-07-23",
                officialSite = "http://www.fake$seriesId.com/shows/hell-on-wheels",
                schedule = SeriesRaw.Schedule(
                    time = "21:00",
                    days = listOf("Monday")
                ),
                rating = SeriesRaw.Rating(
                    average = 8.5
                ),
                weight = seriesId,
                network = SeriesRaw.Network(
                    id = seriesId,
                    name = "Network $seriesId",
                    country = SeriesRaw.Network.Country(
                        name = "United States",
                        code = "US",
                        timezone = "America/New_York"
                    ),
                    officialSite = null
                ),
                webChannel = SeriesRaw.WebChannel(
                    id = seriesId,
                    name = "$seriesId - flix",
                    country = SeriesRaw.Network.Country(
                        name = "United States",
                        code = "US",
                        timezone = "America/New_York"
                    ),
                    officialSite = "https://www.$seriesId-flix.com/"
                ),
                externals = SeriesRaw.Externals(
                    tvRage = seriesId.toLong(),
                    theTvDb = seriesId.toLong(),
                    imdb = "tt221112$seriesId"
                ),
                image = SeriesRaw.Image(
                    medium = "",
                    original = ""
                ),
                summary = "<p><b>The Mindy Project</b> is a single-camera comedy series which follows a skilled OB/GYN navigating the tricky waters of both her personal and professional life.</p>,",
                updated = 1633454845,
                links = SeriesRaw.Links(
                    self = SeriesRaw.Links.Self(
                        href = "https://api.tvmaze.com/shows/75"
                    ),
                    previousEpisode = SeriesRaw.Links.PreviousEpisode(
                        href = "https://api.tvmaze.com/episodes/1334456"
                    )
                ),
            )

        fun createSeriesListFakeList(amount: Int): List<SeriesRaw>{
            val seriesList = arrayListOf<SeriesRaw>()
            for (i in 0 until amount){
                seriesList.add(
                    SeriesRaw(
                 id = i,
                 url = "url_test_$i",
                 name = "Show $i",
                 type = "Tv Series",
                 language = "English",
                 genres = listOf(
                     "Comedy",
                     "Drama",
                     "Science"
                 ),
                 status = "Ended",
                 runtime = 60,
                 averageRuntime = 60,
                 premiered = "011-11-06",
                 ended = "2016-07-23",
                 officialSite = "http://www.fake$i.com/shows/hell-on-wheels",
                 schedule = SeriesRaw.Schedule(
                     time = "21:00",
                     days = listOf("Monday")
                 ),
                 rating = SeriesRaw.Rating(
                     average = 8.5
                 ),
                 weight = i,
                 network = SeriesRaw.Network(
                     id = i,
                     name = "Network $i",
                     country = SeriesRaw.Network.Country(
                         name = "United States",
                         code = "US",
                         timezone = "America/New_York"
                     ),
                     officialSite = null
                 ),
                 webChannel = SeriesRaw.WebChannel(
                     id = i,
                     name = "$i - flix",
                     country = SeriesRaw.Network.Country(
                         name = "United States",
                         code = "US",
                         timezone = "America/New_York"
                     ),
                     officialSite = "https://www.$i-flix.com/"
                 ),
                 externals = SeriesRaw.Externals(
                     tvRage = i.toLong(),
                     theTvDb = i.toLong(),
                     imdb = "tt221112$i"
                 ),
                 image = SeriesRaw.Image(
                     medium = "",
                     original = ""
                 ),
                 summary = "<p><b>The Mindy Project</b> is a single-camera comedy series which follows a skilled OB/GYN navigating the tricky waters of both her personal and professional life.</p>,",
                 updated = 1633454845,
                 links = SeriesRaw.Links(
                     self = SeriesRaw.Links.Self(
                         href = "https://api.tvmaze.com/shows/75"
                     ),
                     previousEpisode = SeriesRaw.Links.PreviousEpisode(
                         href = "https://api.tvmaze.com/episodes/1334456"
                     )
                 ),
                    )
                )
            }
            return seriesList
        }
    }
}