package com.rummenigged.popcorntime.view.seriesList

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.rummenigged.popcorntime.R
import com.rummenigged.popcorntime.databinding.FragmentSeriesListBinding
import com.rummenigged.popcorntime.view.common.BaseFragment
import com.rummenigged.popcorntime.view.common.viewBinding
import com.rummenigged.popcorntime.view.seriesList.adapter.SeriesAdapter
import com.rummenigged.popcorntime.view.utils.gone
import com.rummenigged.popcorntime.view.utils.navigateTo
import com.rummenigged.popcorntime.view.utils.observe
import com.rummenigged.popcorntime.view.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SeriesListFragment : BaseFragment(R.layout.fragment_series_list), SearchView.OnQueryTextListener {

    private val binding by viewBinding(FragmentSeriesListBinding::bind)

    @Inject lateinit var seriesAdapter: SeriesAdapter

    private val seriesViewModel: SeriesViewModel by viewModels()

    override fun setupView(savedInstanceState: Bundle?) {
        super.setupView(savedInstanceState)
        binding.svSeries.setOnQueryTextListener(this)
        setupRecyclerView()
    }

    override fun setupObservers() {
        observe(seriesViewModel.seriesUiState){
            binding.apply {
                pbSeriesList.apply {
                    if (it.isLoading){
                        visible()
                        rvSeries.gone()
                    } else{
                        gone()
                        rvSeries.visible()
                    }
                }

                it.errorMessage?.let {
                    mtvErrorSeriesList.text = it
                }

                it.seriesList?.let { seriesList ->
                    seriesAdapter.swapData(seriesList)
                }
            }
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
                    getItem(position).also { item ->
                        navigateTo(SeriesListFragmentDirections.toSeriesDetailsFragment(item.id))
                    }
                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean =
        seriesViewModel.searchSeries(query.orEmpty()).let {
            true
        }

    override fun onQueryTextChange(newText: String?): Boolean {
        if ((newText?.length ?: 0) > 2){
            seriesViewModel.searchSeries(newText.orEmpty())
        }else if (newText.isNullOrEmpty()){
            seriesViewModel.getSeriesList()
        }
        return true
    }
}