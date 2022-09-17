package com.rummenigged.popcorntime.view

data class EpisodeDetailsUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val episodeDetails: EpisodeDetailsView? = null
)