package com.rummenigged.popcorntime.view.seriesList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.rummenigged.popcorntime.R
import com.rummenigged.popcorntime.databinding.ItemSeriesBinding
import com.rummenigged.popcorntime.view.common.DiffUtilBaseAdapter
import com.rummenigged.popcorntime.view.seriesList.model.SeriesView
import com.rummenigged.popcorntime.view.utils.load
import javax.inject.Inject

class SeriesAdapter @Inject constructor()
    : DiffUtilBaseAdapter<SeriesView>(ArrayList()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SeriesView> =
        when(viewType){
            R.layout.item_series -> {
                SeriesViewHolder(
                    ItemSeriesBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false)
                )
            }

            else -> throw IllegalArgumentException("Unexpected viewType received: $viewType.")
        }

    override fun getItemViewType(position: Int): Int = R.layout.item_series

    inner class SeriesViewHolder(
        private val viewBinding: ItemSeriesBinding
    ):BaseViewHolder<SeriesView>(viewBinding) {
        override fun bind(item: SeriesView) {
            viewBinding.apply {
                ivSeriesBanner.load(item.bannerUrl)
                mtvSeriesName.text = item.name
            }
        }
    }
}