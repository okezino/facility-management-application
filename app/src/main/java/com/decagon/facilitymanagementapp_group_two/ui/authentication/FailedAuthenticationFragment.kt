package com.decagon.facilitymanagementapp_group_two.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentFailedAuthenticationBinding
import com.decagon.facilitymanagementapp_group_two.network.NetworkManager

class FailedAuthenticationFragment : Fragment() {
    private var _binding: FragmentFailedAuthenticationBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFailedAuthenticationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentFailedAuthenticationTryAgainBtn.setOnClickListener {
            findNavController().navigate(R.id.authorizingUserFragment)
        }

        binding.fragmentFailedAuthenticationBackBtn.setOnClickListener {
            findNavController().navigate(R.id.onboardingFragment)
        }

        binding.fragmentFailedAuthenticationBackNavigation.setOnClickListener {
            findNavController().navigate(R.id.onboardingFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
