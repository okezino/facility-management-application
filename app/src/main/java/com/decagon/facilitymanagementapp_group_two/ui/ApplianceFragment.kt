package com.decagon.facilitymanagementapp_group_two.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.facilitymanagementapp_group_two.adapter.ApplianceComplainAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentApplianceBinding


class ApplianceFragment : Fragment() {

    /**
     * Declaration of FragmentAppliance and initialization of Apartment Adapter
     */

    private var _binding : FragmentApplianceBinding? = null
     private val binding
    get() = _binding!!
    var applianceAdapter = ApplianceComplainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        /**
         * binding layout initialization
         */
        _binding = FragmentApplianceBinding.inflate(inflater,container,false)

        /**
         * Creates the layout manager and adapter for the recycler that shows the list of Complains
         */

        var applianceRecyclerView = binding.applianceRecyclerView
        applianceRecyclerView.adapter = applianceAdapter
        applianceRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}