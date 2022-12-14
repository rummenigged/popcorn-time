package com.rummenigged.popcorntime.view.episodeDetails.model

data class EpisodeDetailsView(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val number: Int,
    val season: String,
    val summary: String,
)