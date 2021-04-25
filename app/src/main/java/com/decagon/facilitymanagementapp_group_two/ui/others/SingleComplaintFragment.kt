package com.decagon.facilitymanagementapp_group_two.ui.others

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.adapter.SingleComplaintAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentSingleComplaintBinding
import com.decagon.facilitymanagementapp_group_two.model.data.Comment
import com.decagon.facilitymanagementapp_group_two.network.ApiResponseHandler
import com.decagon.facilitymanagementapp_group_two.utils.setStatusBarBaseColor
import com.decagon.facilitymanagementapp_group_two.viewmodel.SingleComplaintViewModel
import com.decagon.facilitymanagementapp_group_two.viewmodel.SubmitRequestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleComplaintFragment : Fragment() {
    private lateinit var singleComplaintAdapter: SingleComplaintAdapter
    private val viewModel : SingleComplaintViewModel by viewModels()
    private var _binding: FragmentSingleComplaintBinding? = null
    private val binding
        get() = _binding!!
    private val args by navArgs<SingleComplaintFragmentArgs>()
    private lateinit var complaintId : String
    private lateinit var complaintTitle : String
    private lateinit var complaintBody : String

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

        val response = viewModel.getRequestById(complaintId)
      //  viewModel.getRequestFromDb(complaintId)
        ApiResponseHandler(response,this){
           // submitViewModel.saveRequestToDb(it.value.data)
            Log.d("Testing1", "onViewCreated: ${it.value.data.comments}")
            val comments = it.value.data.comments
            if (comments != null) {

                singleComplaintAdapter = SingleComplaintAdapter(comments)
                binding.fragmentSingleComplaintComplaintsRecylcerView.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = singleComplaintAdapter
                }
                binding.fragmentSingleComplaintCommentCountTv.text = "${comments.size}"

                binding.fragmentSingleComplaintComplaintsRecylcerView.visibility = View.VISIBLE
                binding.fragmentSingleComplaintCommentCountTv.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE

            }
//            viewModel.getRequestFromDb(complaintId).observe(viewLifecycleOwner, { request ->
//                Log.d("Testing", "onViewCreated: ${response.comm}")
//
//                request.comments?.let { it1 -> singleComplaintAdapter.setupItems(it1) }
//            })
        }
        /**
         * Creates the layout manager and adapter for the recycler that shows the list of comments
         * to display placeholder data and test scrolling
         */

        binding.fragmentSingleComplaintComplaintTitleTv.text = complaintTitle
        binding.fragmentSingleComplaintComplaintBodyTv.text = complaintBody

        binding.fragmentSingleComplaintPostIv.setOnClickListener {
            val comment = binding.fragmentSingleComplaintWriteACommentEt.text.toString()
            val serverResponse = viewModel.postNewComment(complaintId,comment)
            ApiResponseHandler(serverResponse,this, view){
                Toast.makeText(requireContext(),it.value.message, Toast.LENGTH_SHORT).show()
            }
        }
        binding.fragmentSingleComplaintBackIv.setOnClickListener {
            findNavController().popBackStack()
           // findNavController().navigate(R.id.dashboardFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
