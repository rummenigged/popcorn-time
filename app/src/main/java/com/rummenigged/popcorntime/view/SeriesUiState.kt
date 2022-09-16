package com.rummenigged.popcorntime.view

data class SeriesUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val seriesList: List<SeriesView> = emptyList()
) {
    val hasError: Boolean
    get() = errorMessage.isNotEmpty()
}