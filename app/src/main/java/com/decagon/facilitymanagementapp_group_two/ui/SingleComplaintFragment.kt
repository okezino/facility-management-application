package com.decagon.facilitymanagementapp_group_two.ui

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.facilitymanagementapp_group_two.adapter.SingleComplaintAdapter
import com.decagon.facilitymanagementapp_group_two.data.Comment
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentSingleComplaintBinding

class SingleComplaintFragment : Fragment() {
    private lateinit var singleComplaintAdapter: SingleComplaintAdapter
    private var _binding: FragmentSingleComplaintBinding? = null
    private val binding
        get() = _binding!!

    /**
     * PlaceHolder data for comment recyclerView
     */
    private val items = mutableListOf(
        Comment("Femi"), Comment("Shade"), Comment("Tolu"),
        Comment("Gbemi"), Comment("Sulaimon")
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = requireActivity().window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = requireContext().getColor(R.color.grey_100)
        }

        _binding = FragmentSingleComplaintBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        singleComplaintAdapter = SingleComplaintAdapter()

        /**
         * Creates the layout manager and adapter for the recycler that shows the list of comments
         * to display placeholder data and text scrolling
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
