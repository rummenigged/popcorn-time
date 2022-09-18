package com.rummenigged.popcorntime.view.episodeDetails

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.rummenigged.popcorntime.R
import com.rummenigged.popcorntime.databinding.FragmentEpisodeDetailsBinding
import com.rummenigged.popcorntime.view.common.BaseFragment
import com.rummenigged.popcorntime.view.common.viewBinding
import com.rummenigged.popcorntime.view.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeDetailsFragment : BaseFragment(R.layout.fragment_episode_details) {

    private val binding by viewBinding(FragmentEpisodeDetailsBinding::bind)

    private val episodeDetailsViewModel: EpisodeDetailsViewModel by viewModels()

    private val args: EpisodeDetailsFragmentArgs by navArgs()

    override fun setupObservers() {
        observe(episodeDetailsViewModel.episodeDetailsUiState){
            binding.apply {
                it.episodeDetails?.also { details ->
                    acivEpisodeDetailBanner.load(details.imageUrl)
                    mtvEpisodeDetailName.text = details.name
                    mtvEpisodeDetailSeason.text = details.season
                    mtvEpisodeDetailSummary.text = details.summary.fromHTML()
                }

                it.isLoading.also {
                    if (it){
                        pbEpisodeDetails.visible()
                        acivEpisodeDetailBanner.gone()
                        mtvEpisodeDetailName.gone()
                        mtvEpisodeDetailSeason.gone()
                        mtvEpisodeDetailSummary.gone()
                    }else{
                        pbEpisodeDetails.gone()
                        acivEpisodeDetailBanner.visible()
                        mtvEpisodeDetailName.visible()
                        mtvEpisodeDetailSeason.visible()
                        mtvEpisodeDetailSummary.visible()
                    }
                }

                it.errorMessage?.also {
                    if (it.isNotEmpty()){
                        showLongToastMessage(it)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        episodeDetailsViewModel.getEpisodeDetails(args.url)
    }
}