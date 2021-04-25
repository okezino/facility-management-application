package com.decagon.facilitymanagementapp_group_two.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileDetails
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileImageResponse
import com.decagon.facilitymanagementapp_group_two.model.data.entities.UserData
import com.decagon.facilitymanagementapp_group_two.model.repository.auth.AuthRepository
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
import com.decagon.facilitymanagementapp_group_two.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    val userData = authRepository.getUserFromDb()

    /**
     * Method to upload profile image to the server and keeps the user aware
     * of the various states of the transaction or process.
     */
    fun uploadProfileImage(image: MultipartBody.Part):
        LiveData<ResultStatus<UpdateProfileImageResponse>> {
            val result = MutableLiveData<ResultStatus<UpdateProfileImageResponse>>()
            result.value = ResultStatus.Loading("Updating profile image... Please wait")
            viewModelScope.launch {
                result.value = authRepository.updateProfileImage(image)
            }
            return result
        }

    /**
     * Method to update and upload profile details to the server and keeps the user aware
     * of the various states of the transaction or process.
     */
    fun updateProfileDetails(updateProfileDetails: UpdateProfileDetails):
        LiveData<ResultStatus<Response<Unit>>> {
            val response = MutableLiveData<ResultStatus<Response<Unit>>>()
            response.value = ResultStatus.Loading("Updating profile details... Please wait")
            viewModelScope.launch {
                response.value = authRepository.updateProfileDetails(updateProfileDetails)
            }
            return response
        }

    fun saveData(key: String, value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.saveDataInPref(key, value)
        }
    }

    fun updateUserToDataBase(userData: UserData) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.updateUser(userData)
        }
    }
}
