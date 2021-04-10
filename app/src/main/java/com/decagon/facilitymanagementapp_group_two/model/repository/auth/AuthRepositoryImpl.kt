package com.decagon.facilitymanagementapp_group_two.model.repository.auth

import android.content.SharedPreferences
import com.decagon.facilitymanagementapp_group_two.model.data.ResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.SsoResultBody
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileDetails
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileImageResponse
import com.decagon.facilitymanagementapp_group_two.network.ApiService
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
import com.decagon.facilitymanagementapp_group_two.network.safeApiCall
import okhttp3.MultipartBody
import retrofit2.Response

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

//    override suspend fun updateProfileDetails(updateProfileDetails: UpdateProfileDetails) {
//        val token = "Bearer " + sharedPreferences.getString(TOKEN_NAME, null)
//        apiService.updateProfileDetails(token, updateProfileDetails)
//    }

    override suspend fun updateProfileDetails(updateProfileDetails: UpdateProfileDetails):
        ResultStatus<Response<Unit>> {
            return safeApiCall { apiService.updateProfileDetails(updateProfileDetails) }
        }

    override fun saveDataInPref(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }
}
