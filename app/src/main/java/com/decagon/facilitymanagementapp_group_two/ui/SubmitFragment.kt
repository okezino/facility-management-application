package com.decagon.facilitymanagementapp_group_two.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentSubmitBinding

class SubmitFragment : Fragment() {
    /**
     * Declaration of FragmentSubmitBinding and initialization of Apartment Adapter
     */

    private var _binding: FragmentSubmitBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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
        }

        binding.requestDescription.doOnTextChanged { text, start, before, count ->
            binding.requestDescriptionLayout.error = null
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
        val requestTitle = binding.requestSubject.text.toString()
        val requestDes = binding.requestDescription.text.toString()

        if (requestDes.isNotEmpty() && requestTitle.isNotEmpty() && requestCategory != "Select a Category...") {
            val user = Request(1, requestCategory, "Simon", requestTitle, requestDes, "today", null)
            Toast.makeText(requireContext(), user.toString(), Toast.LENGTH_SHORT).show()
        } else {
            if (requestDes.isEmpty()) binding.requestDescriptionLayout.error = "Request description needed"

            if (requestTitle.isEmpty()) binding.requestSubjectLayout.error = "Request subject needed"

            if (requestCategory == "Select a Category...") binding.feedCategoryLayout.error =
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
        var listOfFeeds = resources.getStringArray(R.array.selection_field)
        var feedArrayAdapter =
            ArrayAdapter(requireContext(), R.layout.feed_selection_item, listOfFeeds)
        binding.selectFeedCategory.setAdapter(feedArrayAdapter)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
