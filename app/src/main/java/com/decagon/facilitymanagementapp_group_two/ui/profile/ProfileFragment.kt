package com.decagon.facilitymanagementapp_group_two.ui.profile

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentProfileBinding
import com.decagon.facilitymanagementapp_group_two.model.data.SsoResultBody
import com.decagon.facilitymanagementapp_group_two.ms_auth.MsWebAuthentication
import com.decagon.facilitymanagementapp_group_two.utils.*
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var userDetails: SsoResultBody
    private lateinit var profileImageContainer: MaterialCardView

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /**
         * Gets SSO details from sharedPreference
         */
        val firstName = sharedPreferences.getString(FIRST_NAME, null)
        val lastName = sharedPreferences.getString(LAST_NAME, null)
        val email = sharedPreferences.getString(EMAIL, null)
        userDetails = SsoResultBody(firstName!!, lastName!!, email!!)
        /**
         * (Temp)-Gets token from shared preference
         */
        val token = sharedPreferences.getString(TOKEN_NAME, null)
        token?.let {
            Log.d("FragmentProfile", it)
        }

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

        // Populates profile page with SSO details
        val userFullName = "${userDetails.firstName} ${userDetails.lastName}"
        binding.fragmentProfileMainName.text = userFullName
        binding.fragmentProfileName.text = userFullName
        binding.fragmentProfileEmail.text = userDetails.email

        // Sign out the current user when the sign out button is clicked.
        binding.fragmentProfileBtnLogout.setOnClickListener {
            MsWebAuthentication.signOutUser(this)
        }

        profileImageContainer = binding.fragmentProfileCv

        // Update profile image with the uploaded image from the user
        val imgUrl = sharedPreferences.getString(PROFILE_IMG_URI, null)
        imgUrl?.let { binding.fragmentProfileImage.loadImage(it) }

    }
}
