package com.decagon.facilitymanagementapp_group_two.ui.others

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.adapter.SingleComplaintAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentSingleComplaintBinding
import com.decagon.facilitymanagementapp_group_two.model.data.Comment
import com.decagon.facilitymanagementapp_group_two.ui.authentication.SuccessfulAuthFragmentArgs
import com.decagon.facilitymanagementapp_group_two.utils.setStatusBarBaseColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleComplaintFragment : Fragment() {
    private lateinit var singleComplaintAdapter: SingleComplaintAdapter
    private var _binding: FragmentSingleComplaintBinding? = null
    private val binding
        get() = _binding!!
    private val args by navArgs<SingleComplaintFragmentArgs>()
    private lateinit var complaintId : String
    private lateinit var complaintTitle : String
    private lateinit var complaintBody : String

    /**
     * PlaceHolder data for comment recyclerView
     */
    private val items = mutableListOf(
        Comment(R.drawable.jack, "Jorge Watson", "2 hours ago", "Eum dicta fuisset phaedrum ei."),
        Comment(R.drawable.homeland, "Kathryn Cooper", "1 hour ago", "An summo saepe maiestatis sit, ei saepe lobortis senserit eos."),
        Comment(R.drawable.jack, "Tolulope Longe", "3 hours ago", "An summo saepe maiestatis sit, ei saepe lobortis senserit eos."),
        Comment(R.drawable.homeland, "Gbemi Sulaimon", "1 hour ago", "Eum dicta fuisset phaedrum ei."),
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /**
         * This sets the status bar to grey for the single complaint fragment if version code greater
         * than or equal marshmallow else maintains the default status bar color
         */
        setStatusBarBaseColor(requireActivity(), requireContext())

        _binding = FragmentSingleComplaintBinding.inflate(inflater, container, false)
        complaintId = args.complaintId.toString()
        complaintTitle = args.complaintTitle.toString()
        complaintBody = args.complaintBody.toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        singleComplaintAdapter = SingleComplaintAdapter()
        binding.fragmentSingleComplaintComplaintTitleTv.text = complaintTitle
        binding.fragmentSingleComplaintComplaintBodyTv.text = complaintBody

        /**
         * Creates the layout manager and adapter for the recycler that shows the list of comments
         * to display placeholder data and test scrolling
         */
        binding.fragmentSingleComplaintComplaintsRecylcerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = singleComplaintAdapter
        }
        singleComplaintAdapter.setupItems(items)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
