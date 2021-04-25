package com.decagon.facilitymanagementapp_group_two.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentSuccessfulAuthBinding
import com.decagon.facilitymanagementapp_group_two.network.ApiResponseHandler
import com.decagon.facilitymanagementapp_group_two.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class SuccessfulAuthFragment : Fragment() {
    private var _binding: FragmentSuccessfulAuthBinding? = null
    private val binding
        get() = _binding!!
    private val args by navArgs<SuccessfulAuthFragmentArgs>()
    private lateinit var userName: String
    private var isProfileCompleted = false
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userName = args.userName
        isProfileCompleted = args.flag
        _binding = FragmentSuccessfulAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            fragmentSuccessfulAuthMsgTv.text = getString(
                R.string.fragment_successful_auth_message, userName
            )

            // Gets all feed and adds it to the database
            fragmentSuccessfulAuthBtn.setOnClickListener {
                val response = viewModel.getAllFeeds()
                ApiResponseHandler(response,this@SuccessfulAuthFragment, view){
                    viewModel.saveFeedsIdToPref(it.value.data.items)
                    viewModel.saveFeedToDb(it.value.data.items)
                    if (isProfileCompleted) {
                        findNavController().popBackStack()
                        findNavController().navigate(R.id.dashboardFragment)
                    } else {
                        findNavController().popBackStack()
                        findNavController().navigate(R.id.profileFragment)
                    }
                }
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
