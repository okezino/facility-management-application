package com.decagon.facilitymanagementapp_group_two.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.facilitymanagementapp_group_two.adapter.ApartmentComplainAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentApartmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApartmentFragment : Fragment() {

    /**
     * Declaration of FragmentApartmentBinding and initialization of Apartment Adapter
     */
    private var _binding: FragmentApartmentBinding? = null
    private val binding
        get() = _binding!!
    var apartmentAdapter = ApartmentComplainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * binding layout initialization
         */
        _binding = FragmentApartmentBinding.inflate(inflater, container, false)

        /**
         * Creates the layout manager and adapter for the recycler that shows the list of Complains
         */
        var apartmentRecyclerView = binding.apartmentRecyclerView
        apartmentRecyclerView.adapter = apartmentAdapter
        apartmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
