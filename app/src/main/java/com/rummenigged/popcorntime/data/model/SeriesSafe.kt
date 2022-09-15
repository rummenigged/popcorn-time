package com.rummenigged.popcorntime.data.model

import com.rummenigged.popcorntime.data.common.Safe
import com.rummenigged.popcorntime.domain.Series

data class SeriesSafe(
    val id: Int,
    val url: String,
    val name: String,
    val image: Image,
): Safe<Series> {
    data class Image(
        val medium: String,
        val original: String
    )

    override fun asDomain(): Series =
        Series(
            id = id,
            url = url,
            name = name,
            imageUrl = image.medium
        )
}