package com.decagon.facilitymanagementapp_group_two.ui.feeds

import android.view.View
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
fun initAdapter(binding: FragmentGeneralBinding, adapter: GeneralCompliantAdapter) {
    val recyclerView = binding.generalRecyclerView
    recyclerView.adapter = adapter

    adapter.addLoadStateListener { loadState ->
        val progressBar = binding.progBar
        val emptyText = binding.noItemsTv
        val complainRecyclerView = binding.generalRecyclerView
        when (loadState.refresh) {
            is LoadState.NotLoading -> {
                progressBar.visibility = View.GONE
                if (loadState.refresh is LoadState.NotLoading) {
                    if (loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                        emptyText.visibility = View.VISIBLE
                    } else {
                        emptyText.visibility = View.GONE
                        complainRecyclerView.visibility = View.VISIBLE
                    }
                }
            }
            is LoadState.Loading -> {
                if (adapter.itemCount == 0) {
                    progressBar.visibility = View.VISIBLE
                    emptyText.visibility = View.GONE
                    complainRecyclerView.visibility = View.GONE
                } else {
                    progressBar.visibility = View.GONE
                    complainRecyclerView.visibility = View.VISIBLE
                }
            }
            is LoadState.Error -> {
                progressBar.visibility = View.GONE
                if (adapter.itemCount < 1) {
                    emptyText.visibility = View.VISIBLE
                } else {
                    emptyText.visibility = View.GONE
                    complainRecyclerView.visibility = View.VISIBLE
                }
            }
        }
    }
}