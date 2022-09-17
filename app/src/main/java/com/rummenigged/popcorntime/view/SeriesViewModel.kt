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
class SeriesViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository
): ViewModel() {

    private val _seriesUiState = MutableStateFlow(SeriesUiState())
    val seriesUiState = _seriesUiState.asStateFlow()

    private var getSeriesListJob: Job? = null

    private var currentPage = 0

    fun getSeriesList(){
        getSeriesListJob?.cancel()
        fireLoadingState(true)
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
                    seriesList.let { list ->
                        if (list.isEmpty()){
                            it.copy(
                                seriesList = null,
                                isLoading = false,
                                errorMessage = "There is no Tv Series Available"
                            )
                        }else{
                            it.copy(
                                seriesList = seriesList,
                                isLoading = false,
                            )
                        }
                    }

                }
            }.recoverCatching {
                fireErrorState(true, it.message ?: "Unknown Error")
            }
        }
    }

    private fun fireLoadingState(isLoading: Boolean){
        _seriesUiState.update {
            it.copy(
                isLoading = isLoading,
                seriesList = null)
        }
    }

    private fun fireErrorState(hasError: Boolean, errorMessage: String){
        if (hasError){
            _seriesUiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = errorMessage,
                    seriesList = null)
            }
        }else{
            _seriesUiState.update {
                it.copy(errorMessage = "")
            }
        }
    }
}