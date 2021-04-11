package com.decagon.facilitymanagementapp_group_two.ui.authentication

import android.content.SharedPreferences
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
import com.decagon.facilitymanagementapp_group_two.model.data.ResponseBody
import com.decagon.facilitymanagementapp_group_two.network.ApiResponseHandler
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
import com.decagon.facilitymanagementapp_group_two.utils.TOKEN_NAME
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

    @Inject
    lateinit var sharedPreferences: SharedPreferences

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

        binding.apply {
            fragmentSuccessfulAuthMsgTv.text = getString(
                R.string.fragment_successful_auth_message, userName
            )
            fragmentSuccessfulAuthBtn.setOnClickListener {
                /**
                 * Calls the method from the viewModel that posts SSO details to endpoint, retrieves the
                 * token from the endpoint and navigate to the edit profile page on successful
                 * interaction with the backend
                 */
                val result = viewModel.getToken()

                ApiResponseHandler(result, this@SuccessfulAuthFragment, view) {
                    viewModel.saveData(TOKEN_NAME, it.value.data.token)
                    findNavController().navigate(R.id.profileFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
