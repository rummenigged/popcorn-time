package com.rummenigged.popcorntime.data.model

import com.rummenigged.popcorntime.data.common.Raw
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SeriesRaw(
    val id: Int?,
    val url: String?,
    val name: String?,
    val type: String?,
    val language: String?,
    val genres: List<String>?,
    val status: String?,
    val runtime: Int?,
    val averageRuntime: Int?,
    val premiered: String?,
    val ended: String?,
    val officialSite: String?,
    val schedule: Schedule?,
    val rating: Rating?,
    val weight: Int?,
    val network: Network?,
    val webChannel: WebChannel?,
    val externals: Externals?,
    val image: Image?,
    val summary: String?,
    val updated: Long?,
    @Json(name = "_links") val links: Links?
): Raw<SeriesSafe> {

    override fun asSafe(): SeriesSafe =
        SeriesSafe(
            id = id ?: 0,
            url = url ?: "",
            name = name ?: "No Content",
            summary = summary ?: "No Content",
            image = image?.asSafe() ?: SeriesSafe.Image(
                medium = "",
                original = ""
            ),
            schedule = SeriesSafe.Schedule(
                time = schedule?.time ?: "00:00",
                days = schedule?.days ?: emptyList()
            ),
            genres = genres ?: emptyList()
        )

    data class Schedule(
        val time: String?,
        val days: List<String>?
    )

    data class Rating(
        val average: Double?
    )

    data class Network(
        val id: Int?,
        val name: String?,
        val country: Country?,
        val officialSite: String?
    ){
        data class Country(
            val name: String?,
            val code: String?,
            val timezone: String?
        )
    }

    data class WebChannel(
        val id : Int?,
        val name: String?,
        val country: Network.Country?,
        val officialSite: String?
    )

    data class Externals(
        @Json(name = "tvrage") val tvRage: Long?,
        @Json(name = "thetvdb") val theTvDb: Long?,
        val imdb: String?,
    )

    data class Image(
        val medium: String?,
        val original: String?
    ): Raw<SeriesSafe.Image> {
        override fun asSafe(): SeriesSafe.Image =
            SeriesSafe.Image(
                medium = medium ?: "",
                original = original ?: ""
            )
    }

    data class Links(
        val self: Self?,
        @Json(name = "previousepisode") val previousEpisode: PreviousEpisode?
    ){
        data class Self(
            val href: String?
        )

        data class PreviousEpisode(
            val href: String?
        )
    }
}