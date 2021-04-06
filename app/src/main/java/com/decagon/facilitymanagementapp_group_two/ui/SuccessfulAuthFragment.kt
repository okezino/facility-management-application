package com.decagon.facilitymanagementapp_group_two.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentSuccessfulAuthBinding
import com.decagon.facilitymanagementapp_group_two.ms_auth.MsWebAuthentication
import com.decagon.facilitymanagementapp_group_two.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SuccessfulAuthFragment : Fragment() {
    private var _binding: FragmentSuccessfulAuthBinding? = null
    private val binding
        get() = _binding!!
    private val args by navArgs<SuccessfulAuthFragmentArgs>()
    private lateinit var userName: String
    private val viewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userName = args.userName
        _binding = FragmentSuccessfulAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**
         * Calls the method from the viewModel that posts SSO details to endpoint and retrives the
         * token from the endpoint
         */
        viewModel.getToken()
        binding.apply {
            fragmentSuccessfulAuthMsgTv.text = getString(
                R.string.fragment_successful_auth_message, userName
            )
            fragmentSuccessfulAuthBtn.setOnClickListener {
                val action = SuccessfulAuthFragmentDirections.actionSuccessfulAuthFragmentToProfileFragment(MsWebAuthentication.ssoResultBody)
                findNavController().navigate(action)

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
