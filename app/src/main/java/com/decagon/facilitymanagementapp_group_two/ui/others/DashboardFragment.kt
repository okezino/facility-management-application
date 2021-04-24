package com.decagon.facilitymanagementapp_group_two.ui.others

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.adapter.ComplaintClickListener
import com.decagon.facilitymanagementapp_group_two.adapter.DashboardComplaintAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentDashboardBinding
import com.decagon.facilitymanagementapp_group_two.network.ApiResponseHandler
import com.decagon.facilitymanagementapp_group_two.utils.PROFILE_IMG_URI
import com.decagon.facilitymanagementapp_group_two.utils.loadImage
import com.decagon.facilitymanagementapp_group_two.utils.setStatusBarBaseColor
import com.decagon.facilitymanagementapp_group_two.utils.showSnackBar
import com.decagon.facilitymanagementapp_group_two.viewmodel.FeedsViewModel
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

    private val feedsViewModel by activityViewModels<FeedsViewModel>()

    var complainRecyclerAdapter = DashboardComplaintAdapter(this)

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

        /**
         * Creates the layout manager and adapter for the recycler that shows the list of Complains
         */

        val complainRecyclerView = binding.dashboardComplaintRecyclerView
        complainRecyclerView.adapter = complainRecyclerAdapter
        complainRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        feedsViewModel.myRequest.observe(viewLifecycleOwner, Observer {
            if (it!!.isNotEmpty()) {
                binding.noComplainText.visibility = View.GONE
                complainRecyclerAdapter.loadData(it)
            }
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                AlertDialog.Builder(viewHolder.itemView.context,R.style.MyDialogTheme)
                    .setTitle("Alert")
                    .setMessage("Are you sure you want to delete this Request?")
                    .setPositiveButton("Yes"){_,_->
                        val Id = viewHolder.adapterPosition
                        val complaintId = complainRecyclerAdapter.getComplaintId(Id)
                        val request = complainRecyclerAdapter.getComplain(Id)
                        val serverResponse = feedsViewModel.deleteComplain(complaintId!!)
                       ApiResponseHandler(serverResponse,this@DashboardFragment,view,failedAction = false){
                          if(it.value.success) {
                              feedsViewModel.deleteComplainFromDataBase(request)
                              view?.showSnackBar("Complaint has been successfully Deleted")
                          } else {
                              view?.showSnackBar("Swipe to delete Again or go")
                          }
                       }
                    }.setNegativeButton("Cancel"){_,_->
                        complainRecyclerAdapter.notifyDataSetChanged()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
