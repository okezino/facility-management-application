package com.decagon.facilitymanagementapp_group_two.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentProfileBinding
import com.decagon.facilitymanagementapp_group_two.model.SsoResultBody
import com.decagon.facilitymanagementapp_group_two.utils.setStatusBarBaseColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding
        get() = _binding!!
    private val args by navArgs<ProfileFragmentArgs>()
    private lateinit var userDetails : SsoResultBody

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        userDetails = args.userDetails


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userFullName = "${userDetails.firstName} ${userDetails.lastName}"
        binding.fragmentProfileMainName.text = userFullName
        binding.fragmentProfileName.text = userFullName
        binding.fragmentProfileEmail.text = userDetails.email
    }
}
