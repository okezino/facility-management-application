package com.decagon.facilitymanagementapp_group_two.ui.others

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.adapter.ComplaintClickListener
import com.decagon.facilitymanagementapp_group_two.adapter.MyRequestAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentDashboardBinding
import com.decagon.facilitymanagementapp_group_two.utils.*
import com.decagon.facilitymanagementapp_group_two.network.ApiResponseHandler
import com.decagon.facilitymanagementapp_group_two.ui.MainActivity
import com.decagon.facilitymanagementapp_group_two.utils.PROFILE_IMG_URI
import com.decagon.facilitymanagementapp_group_two.utils.loadImage
import com.decagon.facilitymanagementapp_group_two.utils.setStatusBarBaseColor
import com.decagon.facilitymanagementapp_group_two.utils.showSnackBar
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

        activity?.onBackPressedDispatcher?.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            }
        )
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
        /**
         * Creates the layout manager and adapter for the recycler that shows the list of Complains
         */

        val complainRecyclerView = binding.dashboardComplaintRecyclerView
        complainRecyclerView.adapter = adapter
        complainRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                AlertDialog.Builder(viewHolder.itemView.context, R.style.MyDialogTheme)
                    .setTitle("Alert")
                    .setMessage("Are you sure you want to delete this Request?")
                    .setPositiveButton("Yes") { _, _ ->
                        val Id = viewHolder.adapterPosition
                        val complaintId = adapter.getComplaintId(Id)
                        val request = adapter.getComplain(Id)
                        val serverResponse = feedsViewModel.deleteComplain(complaintId!!)
                        ApiResponseHandler(serverResponse, this@DashboardFragment, view, failedAction = false) {
                            if (it.value.success) {
                                feedsViewModel.deleteComplainFromDataBase(request)
                                view?.showSnackBar("Complaint has been successfully Deleted")
                            } else {
                                view?.showSnackBar("Swipe to delete Again or go")
                            }
                        }
                    }.setNegativeButton("Cancel") { _, _ ->
                        adapter.notifyDataSetChanged()
                        view?.showSnackBar("Swipe to delete Again")
                    }.setCancelable(false)
                    .create()
                    .show()
            }
        }
        ).apply {
            attachToRecyclerView(binding.dashboardComplaintRecyclerView)
        }

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
    override fun onCompalinClicked(title: String?, body: String?, id: String?, time : String?) {
        val action = DashboardFragmentDirections.actionDashboardFragmentToSingleComplaintFragment(id, title, body,time)
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
            }
        }
    }

    /**
     * Set up recyclerView Adapter and notify user's of data state
     */
    private fun initAdapter() {
        adapter.addLoadStateListener { loadState ->
            val progressBar = binding.progBar
            val emptyText = binding.noComplainText
            val complainRecyclerView = binding.dashboardComplaintRecyclerView
            when (loadState.refresh) {
                is LoadState.NotLoading -> {
                    progressBar.visibility = View.GONE
                    if (loadState.refresh is LoadState.NotLoading) {
                        if (loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                            emptyText.visibility = View.VISIBLE
                        } else {
                            emptyText.visibility = View.GONE
                            complainRecyclerView.visibility = View.VISIBLE
                        }
                    }
                }
                is LoadState.Loading -> {
                    if (adapter.itemCount == 0) {
                        progressBar.visibility = View.VISIBLE
                        emptyText.visibility = View.GONE
                        complainRecyclerView.visibility = View.GONE
                    } else {
                        progressBar.visibility = View.GONE
                        complainRecyclerView.visibility = View.VISIBLE
                    }
                }
                is LoadState.Error -> {
                    progressBar.visibility = View.GONE
                    if (adapter.itemCount < 1) {
                        emptyText.visibility = View.VISIBLE
                    } else {
                        emptyText.visibility = View.GONE
                        complainRecyclerView.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    /**
     * method that handles the search logic and functionality
     */
    private fun searchAction(s: Any?) {
        if (binding.noComplainText.isVisible) return

        viewLifecycleOwner.lifecycleScope.launch {
            feedsViewModel.searchMyRequest(s.toString())?.collectLatest {
                adapter.submitData(it)
                if (adapter.itemCount == 0) {
                    binding.dashboardComplaintRecyclerView.visibility = View.GONE
                    binding.fragmentDashboardNoMatch.visibility = View.VISIBLE
                } else {
                    binding.fragmentDashboardNoMatch.visibility = View.GONE
                    binding.dashboardComplaintRecyclerView.visibility = View.VISIBLE
                }
            }
        }
    }
}
