package com.rummenigged.popcorntime.view.seriesDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rummenigged.popcorntime.domain.repository.SeriesRepository
import com.rummenigged.popcorntime.view.seriesDetails.episodes.model.EpisodeView
import com.rummenigged.popcorntime.view.seriesDetails.model.SeriesDetailsUiState
import com.rummenigged.popcorntime.view.seriesDetails.model.SeriesDetailsView
import com.rummenigged.popcorntime.view.seriesDetails.season.SeasonView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
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

    private var getEpisodesJob: Job? = null

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
                if (it !is CancellationException){
                    fireErrorState(true, it.message ?: "Unknown Error")
                }
            }
        }
    }

    fun getEpisodes(seasonId: Int){
        getEpisodesJob?.cancel()
        fireLoadingEpisodesState(true)
        getEpisodesJob = viewModelScope.launch {
            runCatching {
                val episodesList = seriesRepository.getEpisodes(seasonId).map {
                    EpisodeView(
                        id = it.id,
                        number = it.number,
                        name = "${it.number}.${it.name}",
                        imageUrl = it.image.medium,
                        runtime = "${it.runtime}m",
                        summary = it.summary,
                        link = it.link
                    )
                }

                _seriesDetailsUiState.update {
                    it.copy(
                        isLoadingEpisodes = false,
                        episodesList = episodesList
                    )
                }
            }.recoverCatching {
                if (it !is CancellationException){
                    fireErrorState(true, it.message ?: "Unknown Error")
                }
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
                if (it !is CancellationException){
                    fireErrorState(true, it.message ?: "Unknown Error")
                }
            }
        }
    }

    private fun fireLoadingState(isLoading: Boolean){
        _seriesDetailsUiState.update {
            it.copy(
                isLoading = isLoading,
                seriesDetails = null,
                seasonList = null,
                episodesList = null)
        }
    }

    private fun fireLoadingEpisodesState(isLoading: Boolean){
        _seriesDetailsUiState.update {
            it.copy(
                isLoadingEpisodes = isLoading,
                seriesDetails = null,
                seasonList = null,
                episodesList = null)
        }
    }

    private fun fireErrorState(hasError: Boolean, errorMessage: String){
        if (hasError){
            _seriesDetailsUiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = errorMessage,
                    seriesDetails = null,
                    seasonList = null,
                    episodesList = null)
            }
        }else{
            _seriesDetailsUiState.update {
                it.copy(errorMessage = "")
            }
        }
    }
}