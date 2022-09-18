package com.rummenigged.popcorntime.data.model.raw

import com.rummenigged.popcorntime.data.common.Raw
import com.rummenigged.popcorntime.data.model.safe.SeriesSearchResultSafe


data class SeriesSearchResultRaw(
    val show: Show?
): Raw<SeriesSearchResultSafe> {

    override fun asSafe(): SeriesSearchResultSafe =
        SeriesSearchResultSafe(
            id = show?.id ?: 0,
            name = show?.name ?: "",
            image = SeriesSearchResultSafe.Image(
                medium = show?.image?.medium ?: "",
                original = show?.image?.original ?: "'"
            )
        )

    data class Show(
        val id: Int?,
        val name: String?,
        val image: Image?,
    )

    data class Image(
        val medium: String?,
        val original: String?
    ): Raw<SeriesSearchResultSafe.Image> {
        override fun asSafe(): SeriesSearchResultSafe.Image =
            SeriesSearchResultSafe.Image(
                medium = medium ?: "",
                original = original ?: ""
            )
    }
}