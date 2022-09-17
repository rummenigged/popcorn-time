package com.rummenigged.popcorntime.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rummenigged.popcorntime.common.NetworkException
import com.rummenigged.popcorntime.domain.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailsViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository
): ViewModel() {

    private val _episodeDetailsUiState = MutableStateFlow(EpisodeDetailsUiState())
    val episodeDetailsUiState = _episodeDetailsUiState.asStateFlow()

    private var getEpisodeDetailsJob: Job? = null

    fun getEpisodeDetails(url: String){
        getEpisodeDetailsJob?.cancel()
        fireLoadingState(true)
        getEpisodeDetailsJob = viewModelScope.launch {
            runCatching {
                val episodeDetails = seriesRepository.getEpisodeDetails(url).let { episodeDetails ->
                    EpisodeDetailsView(
                        id = episodeDetails.id,
                        imageUrl = episodeDetails.image.medium,
                        name = "${episodeDetails.number}.${episodeDetails.name}",
                        number = episodeDetails.number,
                        season = "Season ${episodeDetails.season}",
                        summary = episodeDetails.summary
                    )
                }

                _episodeDetailsUiState.update {
                    it.copy(
                        episodeDetails = episodeDetails,
                        isLoading = false,
                    )

                }
            }.recoverCatching {
                fireErrorState(true, it.message ?: "Unknown Error")
            }
        }
    }

    private fun fireLoadingState(isLoading: Boolean){
        _episodeDetailsUiState.update {
            it.copy(
                isLoading = isLoading,
                episodeDetails = null)
        }
    }

    private fun fireErrorState(hasError: Boolean, errorMessage: String){
        if (hasError){
            _episodeDetailsUiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = errorMessage,
                    episodeDetails = null)
            }
        }else{
            _episodeDetailsUiState.update {
                it.copy(errorMessage = "")
            }
        }
    }
}