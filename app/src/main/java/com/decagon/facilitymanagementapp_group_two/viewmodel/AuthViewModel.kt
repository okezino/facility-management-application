package com.decagon.facilitymanagementapp_group_two.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.facilitymanagementapp_group_two.model.repository.auth.AuthRepository
import com.decagon.facilitymanagementapp_group_two.ms_auth.MsWebAuthentication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (private val authRepository: AuthRepository) : ViewModel() {

    /**
     * Sends SSO details to endpoint
     */
    fun getToken() {
        viewModelScope.launch {
            authRepository.postAuthDetails(MsWebAuthentication.ssoResultBody)
        }
    }
}
