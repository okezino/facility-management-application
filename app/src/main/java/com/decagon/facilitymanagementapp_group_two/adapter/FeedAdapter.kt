package com.decagon.facilitymanagementapp_group_two.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.decagon.facilitymanagementapp_group_two.ui.feeds.ApartmentFragment
import com.decagon.facilitymanagementapp_group_two.ui.feeds.ApplianceFragment
import com.decagon.facilitymanagementapp_group_two.ui.feeds.FoodFragment
import com.decagon.facilitymanagementapp_group_two.ui.feeds.OthersFragment

class FeedAdapter(fragment: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragment, lifecycle) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> FoodFragment()
            1 -> ApartmentFragment()
            2 -> ApplianceFragment()
            else -> OthersFragment()
        }
    }
}
