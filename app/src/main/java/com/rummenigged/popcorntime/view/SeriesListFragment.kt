package com.rummenigged.popcorntime.view

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.rummenigged.popcorntime.R
import com.rummenigged.popcorntime.databinding.FragmentSeriesListBinding
import com.rummenigged.popcorntime.view.common.BaseFragment
import com.rummenigged.popcorntime.view.common.viewBinding
import com.rummenigged.popcorntime.view.utils.observe
import com.rummenigged.popcorntime.view.utils.showShortToastMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SeriesListFragment : BaseFragment(R.layout.fragment_series_list) {

    private val binding by viewBinding(FragmentSeriesListBinding::bind)

    @Inject lateinit var seriesAdapter: SeriesAdapter

    private val seriesViewModel: SeriesViewModel by viewModels()

    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)
        setupRecyclerView()
    }

    override fun setupObservers() {
        observe(seriesViewModel.seriesUiState){
            seriesAdapter.swapData(it.seriesList)
        }
    }

    override fun onResume() {
        super.onResume()
        seriesViewModel.getSeriesList()
    }
    private fun setupRecyclerView(){
        binding.rvSeries.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = seriesAdapter.apply {
                subscribeToItemSelection { _, position ->
                    showShortToastMessage("Position $position taped")
                }
            }
        }
    }
}