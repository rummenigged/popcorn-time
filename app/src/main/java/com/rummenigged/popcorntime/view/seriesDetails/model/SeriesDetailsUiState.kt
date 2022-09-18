package com.rummenigged.popcorntime.view.seriesDetails.model

import com.rummenigged.popcorntime.view.seriesDetails.episodes.model.EpisodeView
import com.rummenigged.popcorntime.view.seriesDetails.season.SeasonView

data class SeriesDetailsUiState(
    val isLoading: Boolean = false,
    val isLoadingEpisodes: Boolean = false,
    val errorMessage: String? = null,
    val errorEpisodeList: String? = null,
    val seriesDetails: SeriesDetailsView? = null,
    val seasonList: List<SeasonView>? = null,
    val episodesList: List<EpisodeView>? = null
)