package com.rummenigged.popcorntime.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.rummenigged.popcorntime.R
import com.rummenigged.popcorntime.databinding.FragmentSeriesDetailsBinding
import com.rummenigged.popcorntime.view.common.BaseFragment
import com.rummenigged.popcorntime.view.common.viewBinding
import com.rummenigged.popcorntime.view.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesDetailsFragment : BaseFragment(R.layout.fragment_series_details) {

    private val binding by viewBinding(FragmentSeriesDetailsBinding::bind)

    private val seriesDetailsViewModel: SeriesDetailsViewModel by viewModels()

    private val args: SeriesDetailsFragmentArgs by navArgs()

    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)
        binding.spSeriesSeason.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                showLongToastMessage("$p2")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }
    }

    override fun setupObservers() {
        observe(seriesDetailsViewModel.seriesDetailsUiState){ seriesDetails ->
            binding.apply {
                pbSeriesDetailsList.apply { if (seriesDetails.isLoading) visible() else gone() }

                seriesDetails.seriesDetails?.run{
                    acivSeriesDetailBanner.load(posterUrl)
                    mtvSeriesDetailName.text = name
                    mtvSeriesDetailSummary.text = summary.fromHTML()
                    updateGenresList(genres)
                    mtvSeriesDetailSchedule.text = buildScheduleList(schedule)
                }

                seriesDetails.seasonList?.let {
                    updateSeasonList( it.map { season -> season.name })
                }
            }
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

    override fun onResume() {
        super.onResume()
        seriesDetailsViewModel.getSeriesDetails(args.seriesId)
        seriesDetailsViewModel.getSeriesSeason(args.seriesId)
    }
}