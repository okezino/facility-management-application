package com.decagon.facilitymanagementapp_group_two.ui.others

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.adapter.ComplaintClickListener
import com.decagon.facilitymanagementapp_group_two.adapter.SingleComplaintAdapter
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentSingleComplaintBinding
import com.decagon.facilitymanagementapp_group_two.model.data.RatingBody
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.network.ApiResponseHandler
import com.decagon.facilitymanagementapp_group_two.utils.USER_ID
import com.decagon.facilitymanagementapp_group_two.utils.setStatusBarBaseColor
import com.decagon.facilitymanagementapp_group_two.viewmodel.SingleComplaintViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SingleComplaintFragment : Fragment(), ComplaintClickListener {
    private lateinit var singleComplaintAdapter: SingleComplaintAdapter
    private val viewModel: SingleComplaintViewModel by viewModels()
    private var _binding: FragmentSingleComplaintBinding? = null
    private val binding
        get() = _binding!!
    private val args by navArgs<SingleComplaintFragmentArgs>()
    private lateinit var complaintId : String
    private lateinit var complaintTitle : String
    private lateinit var complaintBody : String
    private lateinit var complaintTime : String
    private var likesCount = 0
    private var isLiked = false
    private var request : Request? = null

    @Inject
    lateinit var sharedPreferences: SharedPreferences

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
        complaintTime = args.complaintTime.toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val response = viewModel.getRequestById(complaintId)

        ApiResponseHandler(response,this,networkError = true,view = binding.progressBar, view2 = binding.fragmentSingleComplaintCommentCountProgress, view3 = binding.fragmentSingleComplaintCommentCountTv, view4 = binding.fragmentSingleComplaintLikeCountProgress){
            request = it.value.data

            val comments = it.value.data.comments
            val ratings = it.value.data.ratings
            isLiked = it.value.data.isLiked
            likesCount = it.value.data.totalRatingCount ?: 0
            binding.fragmentSingleComplaintLikesCountTv.text = "$likesCount"

            if (comments != null) {
                /**
                 * Creates the layout manager and adapter for the recycler that shows the list of comments
                 * to display placeholder data and test scrolling
                 */
                if (comments.isEmpty()){
                    binding.fragmentSingleComplaintNoCommentTv.visibility = View.VISIBLE
                }

                singleComplaintAdapter = SingleComplaintAdapter(comments, this)
                binding.fragmentSingleComplaintComplaintsRecylcerView.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = singleComplaintAdapter
                }
                binding.fragmentSingleComplaintCommentCountTv.text = "${comments.size}"
                binding.fragmentSingleComplaintComplaintsRecylcerView.visibility = View.VISIBLE
                binding.fragmentSingleComplaintCommentCountTv.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.fragmentSingleComplaintCommentCountProgress.visibility = View.GONE

            }

            if(ratings != null){
                val userId = sharedPreferences.getString(USER_ID, null)
                for (i in ratings){
                    if (userId == i.userId){
                        isLiked = true
                        binding.fragmentSingleComplaintLikesIconIv.setImageResource(R.drawable.liked)
                    }
                }
                binding.fragmentSingleComplaintLikeCountProgress.visibility = View.GONE
                binding.fragmentSingleComplaintLikesCountTv.visibility = View.VISIBLE
                viewModel.likesCount.observe(viewLifecycleOwner,{likeCount->
                    likesCount = likeCount
                    binding.fragmentSingleComplaintLikesCountTv.text = "$likeCount" })
            }
        }

        binding.fragmentSingleComplaintComplaintTitleTv.text = complaintTitle
        binding.fragmentSingleComplaintComplaintBodyTv.text = complaintBody

        binding.fragmentSingleComplaintLikesIconIv.setOnClickListener {
            if (isLiked){
                   viewModel.getRatingId(complaintId).observe(viewLifecycleOwner,{
                       val deleteResponse = viewModel.deleteRating(it)
                       ApiResponseHandler(deleteResponse,this,view){
                           request?.ratingId = null
                           request?.id = complaintId
                           isLiked = false
                           request?.isLiked = isLiked
                           request?.time = complaintTime
                           request?.let { it1 -> viewModel.reduceRatingCount(it1, likesCount) }
                           binding.fragmentSingleComplaintLikesIconIv.setImageResource(R.drawable.likes)
                       }
                   })
            }
            else{
                val rating = RatingBody(5)
                val serverResponse = viewModel.postRating(complaintId,rating)
                ApiResponseHandler(serverResponse,this,view ){
                    viewModel.addRatingData(it.value.data)
                    isLiked = true
                    request?.isLiked = isLiked
                    request?.id = complaintId
                    request?.time = complaintTime
                    request?.let { it1 -> viewModel.saveRequestToDb(it1,likesCount) }
                    binding.fragmentSingleComplaintLikesIconIv.setImageResource(R.drawable.liked)
                }

            }
            }
        binding.fragmentSingleComplaintBackTv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCompalinClicked(title: String?, body: String?, id: String?,time: String?) {}
}
