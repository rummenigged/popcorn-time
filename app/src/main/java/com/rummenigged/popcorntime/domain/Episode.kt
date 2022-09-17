package com.rummenigged.popcorntime.domain

data class Episode(
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
){
    data class Rating(
        val average: Double
    )

    data class Image(
        val medium: String,
        val original: String
    )
}