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
import com.decagon.facilitymanagementapp_group_two.adapter.GeneralCompliantAdapter
import com.decagon.facilitymanagementapp_group_two.adapter.OtherComplainAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentGeneralBinding
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentOthersBinding
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import com.decagon.facilitymanagementapp_group_two.network.ApiResponseHandler
import com.decagon.facilitymanagementapp_group_two.utils.OTHERS
import com.decagon.facilitymanagementapp_group_two.viewmodel.FeedsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OthersFragment : Fragment() {

    /**
     * Declaration of FragmentOtherstBinding and initialization of Apartment Adapter
     */

    private var _binding: FragmentGeneralBinding? = null
    val binding
        get() = _binding!!

    private val feedsViewModel by activityViewModels<FeedsViewModel>()
    private val adapter = GeneralCompliantAdapter()

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val response = feedsViewModel.getComplaints(sharedPref.getString(OTHERS, "")!!, 1)
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


//        feedsViewModel.feedCategory.observe(viewLifecycleOwner, Observer { feeds ->
//            val (otherFeed) = feeds.filter { it.name == "others" }
//            Log.d("FeedsCateApa", otherFeed.toString())
//            val response = feedsViewModel.getComplaints(otherFeed.id, 1)
//            ApiResponseHandler(response, this, view) {
//                feedsViewModel.saveComplaints(it.value.data.items)
//            }
//        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Creates the layout manager and adapter for the recycler that shows the list of Complains
         */
        val otherRecyclerView = binding.generalRecyclerView
        otherRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        otherRecyclerView.adapter = adapter

        feedsViewModel.otherComplaints.observe(viewLifecycleOwner, Observer {
            Log.d("FeedsCateOther", it.toString())
            if (it!!.isNotEmpty()) {
                adapter.loadData(it)
                Log.d("FeedsCateOther", it.size.toString())
                binding.noItemsTv.visibility = View.GONE
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
