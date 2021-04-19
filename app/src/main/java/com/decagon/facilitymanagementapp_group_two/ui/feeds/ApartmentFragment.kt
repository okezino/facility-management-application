package com.decagon.facilitymanagementapp_group_two.ui.feeds

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.facilitymanagementapp_group_two.adapter.ApartmentComplainAdapter
import com.decagon.facilitymanagementapp_group_two.adapter.GeneralCompliantAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentApartmentBinding
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentGeneralBinding
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import com.decagon.facilitymanagementapp_group_two.network.ApiResponseHandler
import com.decagon.facilitymanagementapp_group_two.utils.APARTMENT
import com.decagon.facilitymanagementapp_group_two.utils.APPLIANCE
import com.decagon.facilitymanagementapp_group_two.utils.OTHERS
import com.decagon.facilitymanagementapp_group_two.viewmodel.FeedsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ApartmentFragment : Fragment() {

    /**
     * Declaration of FragmentApartmentBinding and initialization of Apartment Adapter
     */
    private var _binding: FragmentGeneralBinding? = null
    private val binding
        get() = _binding!!

    private val adapter = GeneralCompliantAdapter()
    private val feedsViewModel by activityViewModels<FeedsViewModel>()

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val response = feedsViewModel.getComplaints(sharedPref.getString(APARTMENT, "")!!, 1)
        ApiResponseHandler(response, this, view) {
            feedsViewModel.saveComplaints(it.value.data.items)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * binding layout initialization
         */
        _binding = FragmentGeneralBinding.inflate(inflater, container, false)

        /**
         * Creates the layout manager and adapter for the recycler that shows the list of Complains
         */




//        feedsViewModel.feedCategory.observe(viewLifecycleOwner, Observer { feeds ->
//            val (apartFeed) = feeds.filter { it.name == "apartment" }
//            Log.d("FeedsCateApa", apartFeed.toString())
//            val response = feedsViewModel.getComplaints(apartFeed.id, 1)
//            ApiResponseHandler(response, this, view) {
//                feedsViewModel.saveComplaints(it.value.data.items)
//            }
//        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apartmentRecyclerView = binding.generalRecyclerView
        apartmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        apartmentRecyclerView.adapter = adapter

        feedsViewModel.apartmentComplaints.observe(viewLifecycleOwner, Observer {
            Log.d("FeedsCateApa", it.toString())
            if (it!!.isNotEmpty()) {
                adapter.loadData(it)
                Log.d("FeedsCateApa", it.size.toString())
                binding.noItemsTv.visibility = View.GONE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
