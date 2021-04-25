package com.decagon.facilitymanagementapp_group_two.ui.feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.facilitymanagementapp_group_two.adapter.GeneralCompliantAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentGeneralBinding
import com.decagon.facilitymanagementapp_group_two.network.ApiResponseHandler
import com.decagon.facilitymanagementapp_group_two.viewmodel.FeedsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodFragment : Fragment() {

    /**
     * Declaration of FoodDashboardBinding and initialization of Dashboard Adapter
     */

    private var _binding: FragmentGeneralBinding? = null
    val binding
        get() = _binding!!

    private val adapter = GeneralCompliantAdapter()
    private val feedsViewModel by activityViewModels<FeedsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**
         * binding layout initialization
         */
        _binding = FragmentGeneralBinding.inflate(inflater, container, false)

        feedsViewModel.foodFeedId.observe(
            viewLifecycleOwner,
            Observer {
                val response = feedsViewModel.getComplaints(it, 2)
                ApiResponseHandler(response, this, view) {
                    feedsViewModel.saveComplaints(it.value.data.items)
                }
            }
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Creates the layout manager and adapter for the recycler that shows the list of Complains
         */
        val foodRecyclerView = binding.generalRecyclerView
        foodRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        foodRecyclerView.adapter = adapter

        feedsViewModel.foodComplaints.observe(
            viewLifecycleOwner,
            Observer {
                if (it!!.isNotEmpty()) {
                    adapter.loadData(it)
                    binding.noItemsTv.visibility = View.GONE
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
