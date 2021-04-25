package com.decagon.facilitymanagementapp_group_two.ui.profile

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentProfileBinding
import com.decagon.facilitymanagementapp_group_two.model.data.SsoResultBody
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileBody
import com.decagon.facilitymanagementapp_group_two.model.data.entities.UserData
import com.decagon.facilitymanagementapp_group_two.ms_auth.MsWebAuthentication
import com.decagon.facilitymanagementapp_group_two.network.NetworkManager
import com.decagon.facilitymanagementapp_group_two.utils.*
import com.decagon.facilitymanagementapp_group_two.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
                findNavController().navigate(R.id.dashboardFragment)
            }

        })
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /**
         * Gets Profile details from DataBase and update the View
         */

        viewModel.userData.observe(viewLifecycleOwner, Observer {user ->


            binding.fragmentProfileStackSquadText.text = "${user.stack} - ${user.squad}"
            val userFullName = "${user.firstName} ${user.lastName}"
            binding.fragmentProfileMainName.text = userFullName
            binding.fragmentProfileName.text = userFullName
            binding.fragmentProfileEmail.text = user.email
            binding.fragmentProfileSquad.text = user.squad
            binding.fragmentProfileStackText.text = user.stack
            binding.fragmentProfileNumber.text = user.phoneNumber

        })



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
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sign out the current user when the sign out button is clicked.
        binding.fragmentProfileBtnLogout.setOnClickListener {
            MsWebAuthentication.signOutUser(this)
        }

        // Update profile image with the uploaded image from the user
        val imgUrl = sharedPreferences.getString(PROFILE_IMG_URI, null)
        imgUrl?.let {
            binding.profileFragmentContainer.loadImage(it)
        }
        binding.profileFragmentContainer.setOnClickListener {
            zoomImage(it, imgUrl, view)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
