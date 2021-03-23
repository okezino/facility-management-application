package com.decagon.facilitymanagementapp_group_two.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.facilitymanagementapp_group_two.adapter.OtherComplainAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentOthersBinding


class OthersFragment : Fragment() {

    /**
     * Declaration of FragmentOtherstBinding and initialization of Apartment Adapter
     */

    var _binding :  FragmentOthersBinding? = null
    val binding
    get() = _binding!!

    var othersAdapter = OtherComplainAdapter()


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

        _binding = FragmentOthersBinding.inflate(inflater,container,false)

        /**
         * Creates the layout manager and adapter for the recycler that shows the list of Complains
         */
        var otherRecyclerView = binding.othersRecyclerView
        otherRecyclerView.adapter = othersAdapter
        otherRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}