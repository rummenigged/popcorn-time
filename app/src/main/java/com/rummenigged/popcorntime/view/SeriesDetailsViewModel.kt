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
class SeriesDetailsViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository
): ViewModel() {

    private val _seriesDetailsUiState = MutableStateFlow(SeriesDetailsUiState())
    val seriesDetailsUiState = _seriesDetailsUiState.asStateFlow()


    private var getSeriesDetailsJob: Job? = null

    private var getSeriesSeasonJob: Job? = null

    fun getSeriesDetails(seriesId: Int){
        getSeriesDetailsJob?.cancel()
        fireLoadingState(true)
        getSeriesDetailsJob = viewModelScope.launch {
            runCatching {
                val seriesDetails = seriesRepository.getSeriesDetail(seriesId).let {
                    SeriesDetailsView(
                        id = it.id,
                        name = it.name,
                        posterUrl = it.posterUrl,
                        schedule = SeriesDetailsView.Schedule(
                            time = it.schedule.time,
                            days = it.schedule.days
                        ),
                        summary = it.summary,
                        genres = it.genres
                    )
                }

                _seriesDetailsUiState.update {
                    it.copy(
                        isLoading = false,
                        seriesDetails = seriesDetails
                    )
                }
            }.recoverCatching {
                fireErrorState(true, it.message ?: "Unknown Error")
            }
        }
    }

    fun getSeriesSeason(seriesId: Int){
        getSeriesSeasonJob?.cancel()
        fireLoadingState(true)
        getSeriesSeasonJob = viewModelScope.launch {
            runCatching {
                val seasonList = seriesRepository.getSeriesSeasons(seriesId).map {
                    SeasonView(
                        id = it.id,
                        number = it.number,
                        name = "Season ${it.number}"
                    )
                }

                _seriesDetailsUiState.update {
                    it.copy(
                        isLoading = false,
                        seasonList = seasonList
                    )
                }
            }.recoverCatching {
                fireErrorState(true, it.message ?: "Unknown Error")
            }
        }
    }

    private fun fireLoadingState(isLoading: Boolean){
        _seriesDetailsUiState.update {
            it.copy(
                isLoading = isLoading,
                seriesDetails = null,
                seasonList = null,)
        }
    }

    private fun fireErrorState(hasError: Boolean, errorMessage: String){
        if (hasError){
            _seriesDetailsUiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = errorMessage,
                    seriesDetails = null,
                    seasonList = null)
            }
        }else{
            _seriesDetailsUiState.update {
                it.copy(errorMessage = "")
            }
        }
    }
}