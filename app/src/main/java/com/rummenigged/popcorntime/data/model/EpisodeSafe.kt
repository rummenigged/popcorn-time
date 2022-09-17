package com.rummenigged.popcorntime.data.model

import com.rummenigged.popcorntime.data.common.Safe
import com.rummenigged.popcorntime.domain.Episode

data class EpisodeSafe(
    val id: Int,
    val url: String,
    val name: String,
    val season: Int,
    val number: Int,
    val airDate: String,
    val runtime: Int,
    val rating: Rating,
    val image: Image,
    val summary: String,
    val link: String
): Safe<Episode> {
    data class Rating(
        val average: Double
    )

    data class Image(
        val medium: String,
        val original: String
    )
    override fun asDomain(): Episode =
        Episode(
            id = id,
            url = url,
            name = name,
            season = season,
            number = number,
            airDate = airDate,
            runtime = runtime,
            rating = Episode.Rating(
                average = rating.average
            ),
            image = Episode.Image(
                medium = image.medium,
                original = image.original
            ),
            summary = summary,
            link = link
        )

}