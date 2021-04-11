package com.decagon.facilitymanagementapp_group_two.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.databinding.OnboardingFragmentBinding
import com.decagon.facilitymanagementapp_group_two.network.NetworkManager

class OnboardingFragment : Fragment() {
    private var _binding: OnboardingFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = OnboardingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NetworkManager(this)

        /**
         * Click listener for the get started button
         */
        binding.fragmentOnboardGetStartedBtn.setOnClickListener {
            findNavController().navigate(R.id.authorizingUserFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
