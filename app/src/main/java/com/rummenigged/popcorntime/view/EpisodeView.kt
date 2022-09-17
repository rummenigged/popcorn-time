package com.rummenigged.popcorntime.view

data class EpisodeView(
    val id: Int,
    val number: Int,
    val name: String,
    val imageUrl: String,
    val runtime: String,
    val summary: String,
    val link: String
)