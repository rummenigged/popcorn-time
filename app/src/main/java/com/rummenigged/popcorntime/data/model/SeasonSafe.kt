package com.rummenigged.popcorntime.data.model

import com.rummenigged.popcorntime.data.common.Safe
import com.rummenigged.popcorntime.domain.Season

data class SeasonSafe(
    val id: Int,
    val url: String,
    val number: Int,
    val episodeOrder: Int,
    val premieredDate: String,
    val endDate: String,
): Safe<Season> {
    override fun asDomain(): Season =
        Season(
            id = id,
            url = url,
            number = number
        )

}