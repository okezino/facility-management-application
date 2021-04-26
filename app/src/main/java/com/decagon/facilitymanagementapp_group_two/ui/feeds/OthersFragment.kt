package com.decagon.facilitymanagementapp_group_two.ui.feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.decagon.facilitymanagementapp_group_two.adapter.ComplaintClickListener
import com.decagon.facilitymanagementapp_group_two.adapter.GeneralCompliantAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentGeneralBinding
import com.decagon.facilitymanagementapp_group_two.model.data.Complain
import com.decagon.facilitymanagementapp_group_two.ui.others.DashboardFragmentDirections
import com.decagon.facilitymanagementapp_group_two.viewmodel.FeedsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OthersFragment : Fragment(), ComplaintClickListener {

    /**
     * Declaration of FragmentOtherstBinding and initialization of Apartment Adapter
     */

    private var _binding: FragmentGeneralBinding? = null
    val binding
        get() = _binding!!

    private val feedsViewModel by viewModels<FeedsViewModel>()
    private val adapter = GeneralCompliantAdapter(this)
    private var otherComplains: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**
         * binding layout initialization
         */
        _binding = FragmentGeneralBinding.inflate(inflater, container, false)

        initAdapter(binding, adapter)
        getMyRequest()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getMyRequest() {
        otherComplains?.cancel()
        otherComplains = viewLifecycleOwner.lifecycleScope.launch {
            feedsViewModel.getOtherComplains().collectLatest {
                adapter.submitData(it)
                if (adapter.itemCount == 0) {
                    binding.generalRecyclerView.visibility = View.GONE
                    binding.noItemsTv.visibility = View.VISIBLE
                } else {
                    binding.noItemsTv.visibility = View.GONE
                    binding.generalRecyclerView.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCompalinClicked(title: String?, body: String?, id: String?) {
        val action = FeedsFragmentDirections.actionFeedsFragmentToSingleComplaintFragment(id, title, body)
        findNavController().navigate(action)
    }
}
