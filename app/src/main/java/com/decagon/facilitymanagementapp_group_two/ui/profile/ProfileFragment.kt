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
import androidx.navigation.fragment.findNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentProfileBinding
import com.decagon.facilitymanagementapp_group_two.model.data.SsoResultBody
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileBody
import com.decagon.facilitymanagementapp_group_two.ms_auth.MsWebAuthentication
import com.decagon.facilitymanagementapp_group_two.network.NetworkManager
import com.decagon.facilitymanagementapp_group_two.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var userDetails: SsoResultBody
    private lateinit var userData : UpdateProfileBody

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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NetworkManager(this)

        val squad = sharedPreferences.getString(SQUAD, null)
        val stack = sharedPreferences.getString(STACK, null)
        val phoneNumber = sharedPreferences.getString(PHONE_NUMBER, null)
        userData = UpdateProfileBody(squad!!,stack!!,phoneNumber!!)
        binding.fragmentProfileStackSquadText.setText("${userData.stack} - ${userData.squad}")

        // Populates profile page with SSO details
        val userFullName = "${userDetails.firstName} ${userDetails.lastName}"
        binding.fragmentProfileMainName.text = userFullName
        binding.fragmentProfileName.text = userFullName
        binding.fragmentProfileEmail.text = userDetails.email
        binding.fragmentProfileSquad.text = userData.squad
        binding.fragmentProfileStackText.text = userData.stack
        binding.fragmentProfileNumber.text = userData.mobile

        // Sign out the current user when the sign out button is clicked.
        binding.fragmentProfileBtnLogout.setOnClickListener {
            MsWebAuthentication.signOutUser(this)
        }

        // Update profile image with the uploaded image from the user
        val imgUrl = sharedPreferences.getString(PROFILE_IMG_URI, null)
        imgUrl?.let { binding.profileFragmentContainer.loadImage(it) }

        binding.profileFragmentContainer.setOnClickListener {
            zoomImage(it, imgUrl, view)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
