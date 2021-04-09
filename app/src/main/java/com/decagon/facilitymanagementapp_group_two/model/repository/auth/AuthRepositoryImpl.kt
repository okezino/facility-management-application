package com.decagon.facilitymanagementapp_group_two.model.repository.auth

import android.content.SharedPreferences
import android.util.Log
import com.decagon.facilitymanagementapp_group_two.model.data.ResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.SsoResultBody
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileImageResponse
import com.decagon.facilitymanagementapp_group_two.network.ApiService
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
import com.decagon.facilitymanagementapp_group_two.network.safeApiCall
import com.decagon.facilitymanagementapp_group_two.utils.PROFILE_IMG_URI
import com.decagon.facilitymanagementapp_group_two.utils.TOKEN_NAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * This repository class abstracts access to the API endpoint, provides a clean API for
 * for send Microsoft SSO details to the external auth endpoint and also receives the responseBody
 * and saves the token in sharedPreference
 */
class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {

    override suspend fun postAuthDetails(ssoResultBody: SsoResultBody): ResultStatus<ResponseBody> {
        return safeApiCall { apiService.postAuthDetails(ssoResultBody) }
    }

    override suspend fun updateProfileImage(image: MultipartBody.Part):
            ResultStatus<UpdateProfileImageResponse> {
        return safeApiCall { apiService.updateProfileImage(image) }
    }

    override fun saveDataInPref(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }
}
