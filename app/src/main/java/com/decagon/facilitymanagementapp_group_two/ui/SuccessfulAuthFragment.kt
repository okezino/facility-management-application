package com.decagon.facilitymanagementapp_group_two.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentSuccessfulAuthBinding
import com.decagon.facilitymanagementapp_group_two.ms_auth.MsWebAuthentication


class SuccessfulAuthFragment : Fragment() {
    private var _binding: FragmentSuccessfulAuthBinding? = null
    private val binding
        get() = _binding!!
    private val args by navArgs<SuccessfulAuthFragmentArgs>()
    private lateinit var userName: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userName = args.userName
//         Inflate the layout for this fragment
        _binding = FragmentSuccessfulAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // update the displayed message with the user's displayName
        binding.fragmentSuccessfulAuthMsgTv.text = getString(
            R.string.fragment_successful_auth_message, userName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}