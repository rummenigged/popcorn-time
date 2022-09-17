package com.rummenigged.popcorntime.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.material.chip.Chip
import com.rummenigged.popcorntime.R
import com.rummenigged.popcorntime.databinding.FragmentSeriesDetailsBinding
import com.rummenigged.popcorntime.view.common.BaseFragment
import com.rummenigged.popcorntime.view.common.viewBinding
import com.rummenigged.popcorntime.view.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SeriesDetailsFragment : BaseFragment(R.layout.fragment_series_details) {

    private val binding by viewBinding(FragmentSeriesDetailsBinding::bind)

    private val seriesDetailsViewModel: SeriesDetailsViewModel by viewModels()

    @Inject
    lateinit var episodesAdapter: EpisodesAdapter

    private val args: SeriesDetailsFragmentArgs by navArgs()

    private var currenEpisodesList: List<SeasonView> = emptyList()

    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)
        setupRecyclerView()
        setupListeners()
    }

    override fun setupObservers() {
        observe(seriesDetailsViewModel.seriesDetailsUiState){ seriesDetails ->
            binding.apply {

                seriesDetails.seriesDetails?.run{
                    acivSeriesDetailBanner.load(posterUrl)
                    mtvSeriesDetailName.text = name
                    mtvSeriesDetailSummary.text = summary.fromHTML()
                    updateGenresList(genres)
                    mtvSeriesDetailSchedule.text = buildScheduleList(schedule)
                }

                seriesDetails.seasonList?.let {
                    currenEpisodesList = it
                    updateSeasonList( it.map { season -> season.name })
                }

                seriesDetails.episodesList?.also {
                    episodesAdapter.swapData(it)
                }

                seriesDetails.errorMessage?.also {
                    showLongToastMessage(it)
                }

                seriesDetails.errorEpisodeList?.also {
                    showLongToastMessage(it)
                }

                seriesDetails.isLoading.also { isLoading ->
                    if (isLoading){
                        pbSeriesDetailsList.visible()
                        spSeriesSeason.gone()
                    }else{
                        pbSeriesDetailsList.gone()
                        spSeriesSeason.visible()
                    }
                }

                seriesDetails.isLoadingEpisodes.also { isLoading ->
                    if (isLoading){
                        pbEpisodesDetailsList.visible()
                        rvEpisodes.gone()
                    }else{
                        pbEpisodesDetailsList.gone()
                        rvEpisodes.visible()
                    }
                }
            }
        }
    }

    private fun setupListeners(){
        binding.spSeriesSeason.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                try{
                    currenEpisodesList[position].also {
                        seriesDetailsViewModel.getEpisodes(it.id)
                    }
                }catch (e: IndexOutOfBoundsException){}

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }
    }

    private fun updateSeasonList(seasons: List<String>){
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            seasons
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }.also {
            binding.spSeriesSeason.adapter = it
        }
    }

    private fun updateGenresList(gendersList: List<String>){
        binding.apply {
            cgSeriesDetailGender.apply {
                for (g in gendersList){
                    addView(
                        Chip(context).apply {
                            text = g
                        }
                    )
                }
            }
        }
    }

    private fun buildScheduleList(schedule: SeriesDetailsView.Schedule): String =
        schedule.days.joinToString(
            prefix = "Exhibited ",
            postfix = " at ${schedule.time}"
        )

    private fun setupRecyclerView(){
        binding.rvEpisodes.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = episodesAdapter.apply {
                subscribeToItemSelection { _, position ->
                    getItem(position).also { item ->
//                        navigateTo(SeriesListFragmentDirections.toSeriesDetailsFragment(item.id))
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        seriesDetailsViewModel.getSeriesDetails(args.seriesId)
        seriesDetailsViewModel.getSeriesSeason(args.seriesId)
    }
}