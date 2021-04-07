package com.decagon.facilitymanagementapp_group_two.model.repository.auth

import com.decagon.facilitymanagementapp_group_two.model.data.SsoResultBody
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileImageResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * Contain declaration of an abstract method which is implemented by the main authRepository
 * class
 */
interface AuthRepository {
    suspend fun postAuthDetails(ssoResultBody: SsoResultBody)

    suspend fun updateProfileImage(image: MultipartBody.Part): UpdateProfileImageResponse
}
