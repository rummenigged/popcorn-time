package com.rummenigged.popcorntime.view

data class SeriesUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val seriesList: List<SeriesView>? = null
)