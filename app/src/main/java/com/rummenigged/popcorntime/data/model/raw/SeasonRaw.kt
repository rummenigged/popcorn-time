package com.rummenigged.popcorntime.data.model.raw

import com.rummenigged.popcorntime.data.common.Raw
import com.rummenigged.popcorntime.data.model.safe.SeasonSafe
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SeasonRaw(
    val id: Int?,
    val url: String?,
    val number: Int?,
    val episodeOrder: Int?,
    val premieredDate: String?,
    val endDate: String?,
): Raw<SeasonSafe> {
    override fun asSafe(): SeasonSafe =
        SeasonSafe(
            id = id ?: 0,
            url = url ?: "",
            number = number ?: 0,
            episodeOrder = episodeOrder ?: 0,
            premieredDate= premieredDate ?: "",
            endDate = endDate ?: ""
        )

}