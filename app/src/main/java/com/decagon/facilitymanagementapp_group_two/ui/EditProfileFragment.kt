package com.decagon.facilitymanagementapp_group_two.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.data.UserProfile
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentEditProfileBinding
import com.decagon.facilitymanagementapp_group_two.utils.phoneNumberValidator
import com.decagon.facilitymanagementapp_group_two.utils.setStatusBarBaseColor
import com.decagon.facilitymanagementapp_group_two.utils.squadInputValidation
import com.decagon.facilitymanagementapp_group_two.utils.stackValidation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
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
         * Inflate the view binding with Edit Fragment Layout
         */
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        /**
         * Navigate back to Profile Fragment
         */
        binding.editFragmentProfileBackBtn.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
        binding.editFragmentProfileBtnSubmit.setOnClickListener {
            updateProfile()
        }

        return binding.root
    }

    private fun updateProfile() {

        /**
         * Collect Input Data from the UI
         */
        val updateStack = binding.editFragmentProfileStackInput.text.toString().toUpperCase().trim()
        val updateSquad = binding.editFragmentProfileSquadInput.text.toString().trim()
        val updatePhoneNumber = binding.editFragmentProfilePhoneNumber.text.toString().trim()
        val username = binding.editFragmentProfileName.text.toString()
        val profileImage = "profileImage"
        val profileEmail = binding.editFragmentProfileMail.text.toString()
        val password = "12342"

        /**
         * Validate Input data and Toast the values
         */

        if (squadInputValidation(updateSquad) && stackValidation(updateStack) && phoneNumberValidator(updatePhoneNumber)) {
            var user = UserProfile(username, profileImage, profileEmail, updatePhoneNumber, updateSquad, updateStack, password)

            Toast.makeText(requireContext(), user.toString(), Toast.LENGTH_SHORT).show()
        } else {
            if (!squadInputValidation(updateSquad)) Toast.makeText(requireContext(), "Invalid Squad", Toast.LENGTH_SHORT).show()
            else if (!stackValidation(updateStack)) Toast.makeText(requireContext(), "Invalid Stack", Toast.LENGTH_SHORT).show()
            else {
                Toast.makeText(requireContext(), "Invalid Phone Number", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
