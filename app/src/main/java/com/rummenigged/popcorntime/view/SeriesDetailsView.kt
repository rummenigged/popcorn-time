package com.rummenigged.popcorntime.view

data class SeriesDetailsView(
    val id: Int,
    val name: String,
    val posterUrl: String,
    val schedule: Schedule,
    val summary: String,
    val genres: List<String>
) {

    data class Schedule(
        val time: String,
        val days: List<String>
    )
}