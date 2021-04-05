package com.decagon.facilitymanagementapp_group_two.model.repository.auth

import com.decagon.facilitymanagementapp_group_two.model.SsoResultBody
/**
 * Contain declaration of an abstract method which is implemented by the main authRepository
 * class
 */
interface AuthRepository {
    suspend fun postAuthDetails(ssoResultBody: SsoResultBody)

}