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
import com.decagon.facilitymanagementapp_group_two.ui.others.DashboardFragmentDirections
import com.decagon.facilitymanagementapp_group_two.viewmodel.FeedsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApplianceFragment : Fragment(), ComplaintClickListener {

    /**
     * Declaration of FragmentAppliance and initialization of Apartment Adapter
     */

    private var _binding: FragmentGeneralBinding? = null
    private val binding
        get() = _binding!!
    private val feedsViewModel by viewModels<FeedsViewModel>()
    val adapter = GeneralCompliantAdapter(this)
    private var appComplains: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**
         * binding layout initialization
         */
        _binding = FragmentGeneralBinding.inflate(inflater, container, false)

        initAdapter(binding, adapter, viewLifecycleOwner)
        getMyRequest()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getMyRequest() {
        appComplains?.cancel()
        appComplains = viewLifecycleOwner.lifecycleScope.launch {
            feedsViewModel.getAppComplains().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onCompalinClicked(title: String?, body: String?, id: String?, time : String?) {
        val action = FeedsFragmentDirections.actionFeedsFragmentToSingleComplaintFragment(id, title, body,time)
        findNavController().navigate(action)
    }
}
