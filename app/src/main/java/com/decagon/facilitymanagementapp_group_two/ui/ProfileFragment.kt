package com.decagon.facilitymanagementapp_group_two.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentProfileBinding
import com.decagon.facilitymanagementapp_group_two.utils.setStatusBarBaseColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
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
         * Update Status Bar Colour
         */
        setStatusBarBaseColor(requireActivity(), requireContext(), R.color.smokeWhite)

        /**
         * Inflate the view binding with Fragment Layout
         */
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        /**
         * Navigate back to Edit Profile Fragment
         */
        binding.fragmentProfileEditBtn.setOnClickListener {
            findNavController().navigate(R.id.editProfileFragment)
        }

        return binding.root
    }
}
