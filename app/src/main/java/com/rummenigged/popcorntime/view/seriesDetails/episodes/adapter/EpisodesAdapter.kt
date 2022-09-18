package com.rummenigged.popcorntime.view.seriesDetails.episodes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.rummenigged.popcorntime.R
import com.rummenigged.popcorntime.databinding.ItemEpisodeBinding
import com.rummenigged.popcorntime.view.common.BaseAdapter
import com.rummenigged.popcorntime.view.seriesDetails.episodes.model.EpisodeView
import com.rummenigged.popcorntime.view.utils.fromHTML
import com.rummenigged.popcorntime.view.utils.load
import javax.inject.Inject

class EpisodesAdapter @Inject constructor()
    : BaseAdapter<EpisodeView>(ArrayList()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<EpisodeView> =
        when(viewType){
            R.layout.item_episode -> {
                EpisodeViewHolder(
                    ItemEpisodeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false)
                )
            }

            else -> throw IllegalArgumentException("Unexpected viewType received: $viewType.")
        }

    override fun getItemViewType(position: Int): Int = R.layout.item_episode

    inner class EpisodeViewHolder(
        private val viewBinding: ItemEpisodeBinding
    ):BaseViewHolder<EpisodeView>(viewBinding) {
        override fun bind(item: EpisodeView) {
            viewBinding.apply {
                viewBinding.apply {
                    ivSeriesBanner.load(item.imageUrl)
                    mtvEpisodeName.text = item.name
                    mtvEpisodeRuntime.text = item.runtime
                    mtvEpisodeSummary.text = item.summary.fromHTML()

                }
            }
        }
    }
}