package com.decagon.facilitymanagementapp_group_two.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.model.data.ResponseBody
import com.decagon.facilitymanagementapp_group_two.model.repository.auth.AuthRepository
import com.decagon.facilitymanagementapp_group_two.ms_auth.MsWebAuthentication
import com.decagon.facilitymanagementapp_group_two.network.ApiResponseHandler
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
import com.decagon.facilitymanagementapp_group_two.utils.TOKEN_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (private val authRepository: AuthRepository) : ViewModel() {

    /**
     * Sends SSO details to endpoint and perform appropriate actions
     * when the response from the network call is successful else
     * notify user's of the error
     */
    fun getToken(view: View?, navController: NavController) {
        viewModelScope.launch {
            val response = authRepository.postAuthDetails(MsWebAuthentication.ssoResultBody)
            val action: (result: ResultStatus<ResponseBody>) -> Unit = {
                if (it is ResultStatus.Success) {
                    authRepository.saveDataInPref(TOKEN_NAME, it.value.data.token)
                    navController.navigate(R.id.profileFragment)
                }
            }
            ApiResponseHandler(response, action, view)
        }
    }
}
