package com.decagon.facilitymanagementapp_group_two.ui.others

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentSubmitBinding
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.network.ApiResponseHandler
import com.decagon.facilitymanagementapp_group_two.utils.*
import com.decagon.facilitymanagementapp_group_two.viewmodel.SubmitRequestViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SubmitFragment : Fragment() {
    /**
     * Declaration of FragmentSubmitBinding and initialization of Apartment Adapter
     */

    private var _binding: FragmentSubmitBinding? = null
    private val binding
        get() = _binding!!

    private val submitViewModel : SubmitRequestViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setStatusBarBaseColor(requireActivity(), requireContext(), R.color.smokeWhite)
        /**
         * This sets the status bar to grey for the single complaint fragment if version code greater
         * than or equal marshmallow else maintains the default status bar color
         */
        setStatusBarBaseColor(requireActivity(), requireContext())

        /**
         * binding layout initialization
         */
        _binding = FragmentSubmitBinding.inflate(inflater, container, false)

        binding.btnSubmit.setOnClickListener {
            addNewRequest()
        }

        /**
         * Back button function to call the previous Viewed Fragment
         */

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        /**
         * fonOnTextChange is use to change the TextInputlayout Error state to null when the Users begins to type
         */

        binding.requestSubject.doOnTextChanged { text, start, before, count ->

            binding.requestSubjectLayout.error = null
            if (text!!.length > 30) binding.requestSubjectLayout.error = "Text length exceeded"
        }

        binding.requestDescription.doOnTextChanged { text, start, before, count ->
            binding.requestDescriptionLayout.error = null
            if (text!!.length > 300) binding.requestDescriptionLayout.error = "Text length exceeded"
        }

        binding.selectFeedCategory.doOnTextChanged { text, start, before, count ->
            binding.feedCategoryLayout.error = null
        }

        return binding.root
    }

    private fun addNewRequest() {
        /**
         * Get all input from the input field
         * Check  for empty field and trigger input layout error state if empty else
         * create a new request object and toast it out for now
         *
         */
        val requestCategory = binding.selectFeedCategory.text.toString()

        // Gets feedId
        submitViewModel.getFeedId(requestCategory.toLowerCase(Locale.ROOT))

        val requestTitle = binding.requestSubject.text.toString().trim()
        val requestDes = binding.requestDescription.text.toString().trim()
        val userId = sharedPreferences.getString(USER_ID,null)

        if (feedSelectionValidation(requestCategory) && subjectValidation(requestDes) && descriptionValidation(requestDes)) {

            val user = Request(title = requestTitle, question = requestDes, userId = userId, type = requestCategory)

            //Obseves feed id result and adds it to the post new request
            submitViewModel.feedId.observe(viewLifecycleOwner, {
                Log.d("FeedID", "addNewRequest: $it")
                val response = submitViewModel.postNewRequest(it, user)
                ApiResponseHandler(response, this, failedAction = true) { request ->
                    submitViewModel.saveRequestToDb(request.value.data)
                    Log.d("RequestDatabase", "addNewRequest: ${request.value.data}")
                    findNavController().popBackStack()
                    findNavController().navigate(R.id.dashboardFragment)
                }
            })
        } else {
            if (requestDes.isEmpty()) binding.requestDescriptionLayout.error = "Request description needed"

            if (requestTitle.isEmpty()) binding.requestSubjectLayout.error = "Request subject needed"

            if (!feedSelectionValidation(requestCategory)) binding.feedCategoryLayout.error =
                "Choose a Category"
        }
    }

    override fun onResume() {
        super.onResume()

        /**
         * Call the Dropdown array
         * setup and implement the dropdown array Adapter
         */
        binding.requestDescription.gravity = Gravity.TOP
        val listOfFeeds = resources.getStringArray(R.array.selection_field)
        val feedArrayAdapter =
            ArrayAdapter(requireContext(), R.layout.feed_selection_item, listOfFeeds)
        binding.selectFeedCategory.setAdapter(feedArrayAdapter)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
