package com.rummenigged.popcorntime.view.seriesList

import com.rummenigged.popcorntime.view.common.DiffUtilItemList

data class SeriesView(
    val id: Int,
    val bannerUrl: String,
    val name: String
): DiffUtilItemList<SeriesView>() {
    override fun isItemEqualTo(other: SeriesView): Boolean =
        id == other.id


    override fun isContentEqualTo(other: SeriesView): Boolean =
        this == other

    companion object{
        fun emptyInstance(): SeriesView =
            SeriesView(
                id = 0,
                bannerUrl = "",
                name = ""
            )
    }
}