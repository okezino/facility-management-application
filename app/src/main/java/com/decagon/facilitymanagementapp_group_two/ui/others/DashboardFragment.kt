package com.decagon.facilitymanagementapp_group_two.ui.others

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.adapter.ComplaintClickListener
import com.decagon.facilitymanagementapp_group_two.adapter.MyRequestAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentDashboardBinding
import com.decagon.facilitymanagementapp_group_two.utils.*
import com.decagon.facilitymanagementapp_group_two.viewmodel.FeedsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment(), ComplaintClickListener {
    /**
     * Declaration of FragmentDashboardBinding and initialization of Dashboard Adapter
     */

    private lateinit var recyclerView: RecyclerView
    private var _binding: FragmentDashboardBinding? = null
    private val binding
        get() = _binding!!

    private val feedsViewModel by viewModels<FeedsViewModel>()
    private val adapter = MyRequestAdapter(this)
    private var getRequest: Job? = null

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
               activity?.finish()
            }
        })
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

        initAdapter()
        getMyRequest()

        binding.addRequest.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_submitFragment)
        }

        // react to text changes in the search bar
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {searchAction(s)}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { searchAction(s) }
            override fun afterTextChanged(s: Editable?) { searchAction(s) }
        })

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
    override fun onCompalinClicked(title: String?, body: String?, id: String?) {
        val action = DashboardFragmentDirections.actionDashboardFragmentToSingleComplaintFragment(id,title,body)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /**
     * Method to get my request
     */
    private fun getMyRequest() {
        getRequest?.cancel()
        getRequest = viewLifecycleOwner.lifecycleScope.launch {
            feedsViewModel.getMyRequests().collectLatest {
                adapter.submitData(it)
                if (adapter.itemCount == 0) {
                    binding.noComplainText.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    recyclerView.visibility = View.VISIBLE
                    binding.noComplainText.visibility = View.GONE
                }
            }
        }
    }

    /**
     * Set up recyclerView Adapter and notify user's of data state
     */
    private fun initAdapter() {
        recyclerView = binding.dashboardComplaintRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.addLoadStateListener { loadState ->
            binding.dashboardComplaintRecyclerView.isVisible =
                loadState.refresh is LoadState.NotLoading
            binding.progBar.isVisible = loadState.refresh is LoadState.Loading
        }
    }

    /**
     * method that handles the search logic and functionality
     */
    private fun searchAction(s: Any?) {
        viewLifecycleOwner.lifecycleScope.launch {
            feedsViewModel.searchMyRequest(s.toString())?.collectLatest {
                adapter.submitData(it)
                if (adapter.itemCount == 0) {
                    recyclerView.visibility = View.GONE
                    binding.fragmentDashboardNoMatch.visibility = View.VISIBLE
                } else {
                    binding.fragmentDashboardNoMatch.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }
        }
    }
}
