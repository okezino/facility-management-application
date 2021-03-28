package com.decagon.facilitymanagementapp_group_two.ui

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentSubmitBinding
import com.google.android.material.textfield.TextInputLayout

class SubmitFragment : Fragment() {

    private var _binding: FragmentSubmitBinding? = null
    private val binding
        get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentSubmitBinding.inflate(inflater, container, false)

        binding.btnSubmit.setOnClickListener {
            addNewRequest()
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }



        binding.requestSubject.doOnTextChanged { text, start, before, count ->
            binding.textInputLayout3.error = null
        }

        binding.requestDescription.doOnTextChanged { text, start, before, count ->
            binding.textInputLayout4.error = null
        }

        binding.selectFeedCategory.doOnTextChanged { text, start, before, count ->
            binding.textInputLayout2.error = null
        }
        return binding.root
    }

    private fun addNewRequest() {

        val requestCategory = binding.selectFeedCategory.text.toString()
        val requestTitle = binding.requestSubject.text.toString()
        val requestDes = binding.requestDescription.text.toString()

        if (requestDes.isNotEmpty() && requestTitle.isNotEmpty() && requestCategory != "Select a Category...") {
            val user = Request(1, requestCategory, "Simon", requestTitle, requestDes, "today", null)
            Toast.makeText(requireContext(), user.toString(), Toast.LENGTH_SHORT).show()

        } else {
            if (requestDes.isEmpty()) binding.textInputLayout4.error = "Request description needed"

            if (requestTitle.isEmpty()) binding.textInputLayout3.error = "Request subject needed"

            if (requestCategory == "Select a Category...") binding.textInputLayout2.error =
                "Choose a Category"

        }


    }

    override fun onResume() {
        super.onResume()

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