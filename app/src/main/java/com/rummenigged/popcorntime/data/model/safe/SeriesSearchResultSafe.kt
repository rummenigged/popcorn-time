package com.rummenigged.popcorntime.data.model.safe

import com.rummenigged.popcorntime.data.common.Safe
import com.rummenigged.popcorntime.domain.model.SeriesSearchResult

data class SeriesSearchResultSafe(
    val id: Int,
    val name: String,
    val image: Image,
): Safe<SeriesSearchResult> {

    data class Image(
        val medium: String,
        val original: String
    )

    override fun asDomain(): SeriesSearchResult =
        SeriesSearchResult(
            id = id,
            name = name,
            imageUrl = image.medium
        )
}