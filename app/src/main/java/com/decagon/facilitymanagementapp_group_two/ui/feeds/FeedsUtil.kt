package com.decagon.facilitymanagementapp_group_two.ui.feeds

import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.decagon.facilitymanagementapp_group_two.adapter.GeneralCompliantAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentGeneralBinding

/**
 * Function to setup adapter for the feeds fragments
 */
fun initAdapter(binding: FragmentGeneralBinding, adapter: GeneralCompliantAdapter) {
    val recyclerView = binding.generalRecyclerView
    recyclerView.adapter = adapter

    adapter.addLoadStateListener { loadState ->
        binding.generalRecyclerView.isVisible = loadState.refresh is LoadState.NotLoading
        binding.progBar.isVisible = loadState.refresh is LoadState.Loading
    }
}