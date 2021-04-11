package com.decagon.facilitymanagementapp_group_two.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.facilitymanagementapp_group_two.model.data.ResponseBody
import com.decagon.facilitymanagementapp_group_two.model.repository.auth.AuthRepository
import com.decagon.facilitymanagementapp_group_two.ms_auth.MsWebAuthentication
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
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
    fun getToken(): LiveData<ResultStatus<ResponseBody>> {
        val response = MutableLiveData<ResultStatus<ResponseBody>>()
        viewModelScope.launch {
            response.value = authRepository.postAuthDetails(MsWebAuthentication.ssoResultBody)
        }
        return response
    }

    fun saveData(key: String, value: String) {
        authRepository.saveDataInPref(key, value)
    }
}
