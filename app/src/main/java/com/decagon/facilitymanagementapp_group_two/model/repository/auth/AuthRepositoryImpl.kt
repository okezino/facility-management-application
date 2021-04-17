package com.decagon.facilitymanagementapp_group_two.model.repository.auth

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.decagon.facilitymanagementapp_group_two.model.data.ResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.SsoResultBody
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileDetails
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileImageResponse
import com.decagon.facilitymanagementapp_group_two.model.data.database.CentralDatabase
import com.decagon.facilitymanagementapp_group_two.model.data.entities.AuthResponse
import com.decagon.facilitymanagementapp_group_two.model.data.entities.User
import com.decagon.facilitymanagementapp_group_two.model.data.entities.UserData
import com.decagon.facilitymanagementapp_group_two.network.ApiResponseHandler
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
    private val sharedPreferences: SharedPreferences,
    private val centralDatabase: CentralDatabase
) : AuthRepository {


    override suspend fun postAuthDetails(token: String): ResultStatus<ResponseBody> {
        return safeApiCall { apiService.postAuthDetails(token) }
    }

    override suspend fun updateProfileImage(image: MultipartBody.Part):
        ResultStatus<UpdateProfileImageResponse> {
            return safeApiCall { apiService.updateProfileImage(image) }
        }

    override suspend fun updateProfileDetails(updateProfileDetails: UpdateProfileDetails):
        ResultStatus<Response<Unit>> {
            return safeApiCall { apiService.updateProfileDetails(updateProfileDetails) }
        }

    override fun saveDataInPref(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override suspend fun saveAccessToken(authResponse: AuthResponse) {
        centralDatabase.authResponseDao.insert(authResponse)
    }

    override suspend fun getAccessToken(): AuthResponse {
       return  centralDatabase.authResponseDao.getAuthResponse()
    }

    override suspend fun getUsers(userId : String): ResultStatus<User> {
       return safeApiCall { apiService.getUser(userId) }
    }

    override suspend fun saveUser(userData: UserData){
       centralDatabase.userDao.insert(userData)
    }

    override fun getUserFromDb(): LiveData<UserData> {
        return centralDatabase.userDao.get()
    }

}
