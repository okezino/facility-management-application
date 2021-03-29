package com.decagon.facilitymanagementapp_group_two.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentAuthorizingUserBinding
import com.decagon.facilitymanagementapp_group_two.ms_auth.MsWebAuthentication
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizingUserFragment : Fragment() {
    private var _binding: FragmentAuthorizingUserBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAuthorizingUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MsWebAuthentication.signInUser(requireActivity(), this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
