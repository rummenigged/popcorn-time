package com.rummenigged.popcorntime.view

data class SeriesDetailsUiState(
    val isLoading: Boolean = false,
    val isLoadingEpisodes: Boolean = false,
    val errorMessage: String? = null,
    val errorEpisodeList: String? = null,
    val seriesDetails: SeriesDetailsView? = null,
    val seasonList: List<SeasonView>? = null,
    val episodesList: List<EpisodeView>? = null
)