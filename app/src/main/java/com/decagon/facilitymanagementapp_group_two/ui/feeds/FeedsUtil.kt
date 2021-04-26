package com.decagon.facilitymanagementapp_group_two.ui.feeds

import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import com.decagon.facilitymanagementapp_group_two.adapter.GeneralCompliantAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentGeneralBinding
import com.decagon.facilitymanagementapp_group_two.ui.MainActivity

/**
 * Function to setup adapter for the feeds fragments
 */
fun initAdapter(binding: FragmentGeneralBinding, adapter: GeneralCompliantAdapter, owner: LifecycleOwner) {
    val recyclerView = binding.generalRecyclerView
    recyclerView.adapter = adapter

    MainActivity.isConnected.observe(owner, Observer {
        if (it) {
            adapter.addLoadStateListener { loadState ->
                binding.generalRecyclerView.isVisible = loadState.mediator?.refresh is LoadState.NotLoading
                binding.progBar.isVisible = loadState.mediator?.refresh is LoadState.Loading
                binding.noItemsTv.isVisible =
                    (loadState.mediator?.refresh is LoadState.NotLoading && adapter.itemCount == 0)
                            || loadState.mediator?.refresh is LoadState.Error
            }
        } else {
            adapter.addLoadStateListener { loadState ->
                binding.generalRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                binding.progBar.isVisible = loadState.source.refresh is LoadState.Loading
                binding.noItemsTv.isVisible =
                    loadState.source.refresh is LoadState.NotLoading && adapter.itemCount == 0
            }
        }
    })

}