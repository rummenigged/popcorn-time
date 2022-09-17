package com.rummenigged.popcorntime.view

import android.os.Bundle
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
            }
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
    }
}