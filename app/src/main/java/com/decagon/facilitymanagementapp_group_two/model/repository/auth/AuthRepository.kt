package com.decagon.facilitymanagementapp_group_two.model.repository.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.decagon.facilitymanagementapp_group_two.model.data.ResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.SsoResultBody
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileDetails
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileImageResponse
import com.decagon.facilitymanagementapp_group_two.model.data.entities.AuthResponse
import com.decagon.facilitymanagementapp_group_two.model.data.entities.User
import com.decagon.facilitymanagementapp_group_two.model.data.entities.UserData
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
import okhttp3.MultipartBody
import retrofit2.Response

/**
 * Contain declaration of an abstract method which is implemented by the main authRepository
 * class
 */
interface AuthRepository {
    suspend fun postAuthDetails(token : String): ResultStatus<ResponseBody>

    suspend fun updateProfileImage(image: MultipartBody.Part): ResultStatus<UpdateProfileImageResponse>

    suspend fun updateProfileDetails(updateProfileDetails: UpdateProfileDetails): ResultStatus<Response<Unit>>

    fun saveDataInPref(key: String, value: String)

    suspend fun saveAccessToken(authResponse: AuthResponse)

    suspend fun getAccessToken(): AuthResponse

    suspend fun getUsers(userId: String):ResultStatus<User>

    suspend fun saveUser(userData: UserData)

     fun getUserFromDb():LiveData<UserData>


}
