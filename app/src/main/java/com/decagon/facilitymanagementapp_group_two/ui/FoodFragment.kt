package com.decagon.facilitymanagementapp_group_two.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.facilitymanagementapp_group_two.adapter.FoodComplainAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentFoodBinding


class FoodFragment : Fragment() {

    /**
     * Declaration of FoodDashboardBinding and initialization of Dashboard Adapter
     */

    var _binding : FragmentFoodBinding? = null
    val binding
     get() = _binding!!

    var foodAdapter = FoodComplainAdapter()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**
         * binding layout initialization
         */
        _binding = FragmentFoodBinding.inflate(inflater,container,false)


        /**
         * Creates the layout manager and adapter for the recycler that shows the list of Complains
         */

        var foodRecyclerView = binding.foodRecyclerView
        foodRecyclerView.adapter = foodAdapter
        foodRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}