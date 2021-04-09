package com.decagon.facilitymanagementapp_group_two.model.repository.auth

import android.content.SharedPreferences
import android.util.Log
import com.decagon.facilitymanagementapp_group_two.model.data.SsoResultBody
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileDetails
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileImageResponse
import com.decagon.facilitymanagementapp_group_two.network.ApiService
import com.decagon.facilitymanagementapp_group_two.utils.TOKEN_NAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

/**
 * This repository class abstracts access to the API endpoint, provides a clean API for
 * for send Microsoft SSO details to the external auth endpoint and also receives the responseBody
 * and saves the token in sharedPreference
 */
class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {

    override suspend fun postAuthDetails(ssoResultBody: SsoResultBody) {
        withContext(Dispatchers.IO) {
            try {
                val responseBody = apiService.postAuthDetails(ssoResultBody)
                val token = responseBody.data.token
                sharedPreferences.edit().putString(TOKEN_NAME, token).apply()
            } catch (e: Exception) {
                Log.d("auth", "${e.message}")
            }
        }
    }

    override suspend fun updateProfileImage(image: MultipartBody.Part): UpdateProfileImageResponse {
        val token = "Bearer " + sharedPreferences.getString(TOKEN_NAME, null)
        return apiService.updateProfileImage(token, image)
    }

    override suspend fun updateProfileDetails(updateProfileDetails: UpdateProfileDetails) {
        val token = "Bearer " + sharedPreferences.getString(TOKEN_NAME, null)
        apiService.updateProfileDetails(token, updateProfileDetails)
    }
}