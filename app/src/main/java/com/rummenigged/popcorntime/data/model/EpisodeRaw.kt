package com.rummenigged.popcorntime.data.model

import com.rummenigged.popcorntime.data.common.Raw
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EpisodeRaw(
    val id: Int?,
    val url: String?,
    val name: String?,
    val season: Int?,
    val number: Int?,
    @Json(name = "airdate") val airDate: String,
    val runtime: Int?,
    val rating: Rating?,
    val image: Image?,
    val summary: String?,
): Raw<EpisodeSafe> {
    data class Rating(
        val average: Double?
    )

    data class Image(
        val medium: String?,
        val original: String?
    )
    override fun asSafe(): EpisodeSafe =
        EpisodeSafe(
            id = id ?: 0,
            url = url ?: "",
            name = name ?: "",
            season = season ?: 0,
            number = number ?: 0,
            airDate = airDate ?: "",
            runtime = runtime ?: 0,
            rating = EpisodeSafe.Rating(
                average = rating?.average ?: 0.0
            ),
            image = EpisodeSafe.Image(
                medium = image?.medium ?: "",
                original = image?.original ?: ""
            ),
            summary = summary ?: "",
        )

}