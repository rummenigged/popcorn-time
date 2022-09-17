package com.rummenigged.popcorntime.view

data class SeriesDetailsUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val seriesDetails: SeriesDetailsView? = null
)