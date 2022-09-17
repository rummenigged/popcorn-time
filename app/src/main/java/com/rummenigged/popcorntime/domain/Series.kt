package com.rummenigged.popcorntime.domain

data class Series(
    val id: Int,
    val url: String,
    val name: String,
    val imageUrl: String,
    val posterUrl: String,
    val schedule: Schedule,
    val summary: String,
    val genres: List<String>
){
    data class Schedule(
        val time: String,
        val days: List<String>
    )
}