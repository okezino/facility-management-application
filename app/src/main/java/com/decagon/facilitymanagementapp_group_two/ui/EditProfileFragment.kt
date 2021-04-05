package com.decagon.facilitymanagementapp_group_two.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.data.UserProfile
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentEditProfileBinding
import com.decagon.facilitymanagementapp_group_two.utils.phoneNumberValidator
import com.decagon.facilitymanagementapp_group_two.utils.setStatusBarBaseColor
import com.decagon.facilitymanagementapp_group_two.utils.squadInputValidation
import com.decagon.facilitymanagementapp_group_two.utils.stackValidation
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private var _binding : FragmentEditProfileBinding? = null
    private val binding
    get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        setStatusBarBaseColor(requireActivity(),requireContext(),R.color.smokeWhite)
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater,container,false)
        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
        binding.btnSubmit.setOnClickListener {
            updateProfile()
        }


        return binding.root
    }

    private fun updateProfile(){

        val updateStack = binding.stackInput.text.toString().toUpperCase().trim()
        val updateSquad = binding.squadInput.text.toString().trim()
        val updatePhoneNumber = binding.phoneNumber.text.toString().trim()
        val username = binding.mainProfileName.text.toString()
        val profileImage = "profileImage"
        val profileEmail = binding.profileMail.text.toString()
        val password = "12342"



        if(squadInputValidation(updateSquad) && stackValidation(updateStack) && phoneNumberValidator(updatePhoneNumber)){
            var user = UserProfile(username,profileImage,profileEmail,updatePhoneNumber,updateSquad,updateStack,password)

            Toast.makeText(requireContext(), user.toString(), Toast.LENGTH_SHORT).show()
        } else {
            if(!squadInputValidation(updateSquad)) Toast.makeText(requireContext(), "Invalid Squad", Toast.LENGTH_SHORT).show()
            else if(!stackValidation(updateStack)) Toast.makeText(requireContext(), "Invalid Stack", Toast.LENGTH_SHORT).show()
            else {
                Toast.makeText(requireContext(), "Invalid Phone Number", Toast.LENGTH_SHORT).show()
            }

        }




    }



}