package com.decagon.facilitymanagementapp_group_two.ui.others

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.adapter.ComplaintClickListener
import com.decagon.facilitymanagementapp_group_two.adapter.DashboardComplaintAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentDashboardBinding
import com.decagon.facilitymanagementapp_group_two.utils.PROFILE_IMG_URI
import com.decagon.facilitymanagementapp_group_two.utils.loadImage
import com.decagon.facilitymanagementapp_group_two.utils.setStatusBarBaseColor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment(), ComplaintClickListener {
    /**
     * Declaration of FragmentDashboardBinding and initialization of Dashboard Adapter
     */

    private var _binding: FragmentDashboardBinding? = null
    private val binding
        get() = _binding!!

    var complainRecycler = DashboardComplaintAdapter(this)

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**
         * This sets the status bar to grey for the single complaint fragment if version code greater
         * than or equal marshmallow else maintains the default status bar color
         */
        setStatusBarBaseColor(requireActivity(), requireContext())

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Upload profile image from shared preference
         */
        val imgUrl = sharedPreferences.getString(PROFILE_IMG_URI, null)
        imgUrl?.let {
            binding.userImage.loadImage(imgUrl)
        }
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
