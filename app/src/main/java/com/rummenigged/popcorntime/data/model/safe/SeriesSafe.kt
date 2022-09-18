package com.rummenigged.popcorntime.data.model.safe

import com.rummenigged.popcorntime.data.common.Safe
import com.rummenigged.popcorntime.domain.model.Series

data class SeriesSafe(
    val id: Int,
    val url: String,
    val name: String,
    val summary: String,
    val image: Image,
    val schedule: Schedule,
    val genres: List<String>,
): Safe<Series> {
    data class Image(
        val medium: String,
        val original: String
    )

    data class Schedule(
        val time: String,
        val days: List<String>
    )

    override fun asDomain(): Series =
        Series(
            id = id,
            url = url,
            name = name,
            imageUrl = image.medium,
            posterUrl = image.original,
            schedule = Series.Schedule(
                time = schedule.time,
                days = schedule.days
            ),
            summary = summary,
            genres = genres
        )
}