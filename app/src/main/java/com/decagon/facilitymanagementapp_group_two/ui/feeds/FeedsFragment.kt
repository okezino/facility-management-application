package com.decagon.facilitymanagementapp_group_two.ui.feeds

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavAction
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.adapter.FeedAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentFeedsBinding
import com.decagon.facilitymanagementapp_group_two.utils.setStatusBarBaseColor
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedsFragment : Fragment() {

    /**
     * Declaration of FragmentFeedBinding and initialization of Apartment Adapter
     */

    private var _binding: FragmentFeedsBinding? = null
    private val binding
        get() = _binding!!
    lateinit var feedAdapter: FeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setStatusBarBaseColor(requireActivity(), requireContext(), R.color.smokeWhite)
        /**
         * binding layout initialization
         */
        _binding = FragmentFeedsBinding.inflate(inflater, container, false)

        /**
         * Declaration and initialization of the  ViewPager_adapter for the TapLayout
         */

        feedAdapter = FeedAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = feedAdapter
        TabLayoutMediator(binding.TabLay, binding.viewPager) { tab: TabLayout.Tab, i: Int ->
            when (i) {
                0 -> tab.apply {
                    text = "Foods"
                }
                1 -> tab.apply {
                    text = "Apartment"
                }
                2 -> tab.apply {
                    text = "Appliance"
                }
                else -> tab.apply {
                    text = "Others"
                }
            }
        }.attach()

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
