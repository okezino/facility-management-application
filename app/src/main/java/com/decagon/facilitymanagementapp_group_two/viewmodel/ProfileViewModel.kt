package com.decagon.facilitymanagementapp_group_two.viewmodel

import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.facilitymanagementapp_group_two.model.repository.auth.AuthRepository
import com.decagon.facilitymanagementapp_group_two.network.ApiCallStatus
import com.decagon.facilitymanagementapp_group_two.utils.PROFILE_IMG_URI
import com.decagon.facilitymanagementapp_group_two.utils.TOKEN_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private val _status = MutableLiveData<ApiCallStatus>()
    val status = _status as LiveData<ApiCallStatus>


    fun uploadProfileImage(image: MultipartBody.Part) {
        _status.value = ApiCallStatus.LOADING
        viewModelScope.launch {
            try {
                val serverResponse = authRepository.updateProfileImage(image)
                Log.d("uploadImageResult", serverResponse.toString())
                sharedPreferences.edit().putString(PROFILE_IMG_URI, serverResponse.data.url).apply()
                _status.value = ApiCallStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("uploadImageError", "${e.message}")
                _status.value = ApiCallStatus.ERROR
            }

        }

    }

}