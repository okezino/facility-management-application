package com.decagon.facilitymanagementapp_group_two.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.adapter.ComplaintClickListener
import com.decagon.facilitymanagementapp_group_two.adapter.DashboardComplaintAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment(),ComplaintClickListener {
    /**
     * Declaration of FragmentDashboardBinding and initialization of Dashboard Adapter
     */
    private var _binding : FragmentDashboardBinding? = null
    private val binding
    get() = _binding!!

    var complainRecycler =  DashboardComplaintAdapter(this)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater,container,false)
        /**
         * Creates the layout manager and adapter for the recycler that shows the list of Complains
         */

        var complainRecyclerView = binding.dashboardComplaintRecyclerView
        complainRecyclerView.adapter = complainRecycler
        complainRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.addRequest.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_submitFragment)
        }

        return binding.root
    }

    /**
     * OnclickListener function to Navigate to the single Complaint Fragment
     */
    override fun onCompalinClicked() {
        findNavController().navigate(R.id.singleComplaintFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}