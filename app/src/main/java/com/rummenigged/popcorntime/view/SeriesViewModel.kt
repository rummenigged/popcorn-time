package com.rummenigged.popcorntime.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rummenigged.popcorntime.domain.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository
): ViewModel() {

    private val _seriesUiState = MutableStateFlow(SeriesUiState())
    val seriesUiState = _seriesUiState.asStateFlow()

    private var getSeriesListJob: Job? = null

    private var currentPage = 0

    fun getSeriesList(){
        getSeriesListJob?.cancel()
        getSeriesListJob = viewModelScope.launch {
            runCatching {
                val seriesList = seriesRepository.getSeriesList(currentPage).map { series ->
                    SeriesView(
                        id = series.id,
                        bannerUrl = series.imageUrl,
                        name = series.name
                    )
                }
                _seriesUiState.update {
                    it.copy(
                        seriesList = seriesList
                    )
                }
            }.recoverCatching {
                _seriesUiState.update {
                    it.copy(
                        errorMessage = it.errorMessage
                    )
                }
            }
        }
    }
}