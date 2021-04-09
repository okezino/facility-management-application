package com.decagon.facilitymanagementapp_group_two.network

import com.decagon.facilitymanagementapp_group_two.model.data.*
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.model.data.entities.User
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    /**
     * Get all comments from Api
     */
    @GET("comments")
    suspend fun getAllComment(): List<Comment>

    /**
     * @param id
     * get User data using the userId
     */

    @GET("user/{id}")
    suspend fun getUser(
        @Path("id") id: Int
    ): User

    /**
     * Get all request from Api
     */

    @GET("request")
    suspend fun getAllRequest(): List<Request>

    /**
     * Post SSO result to the endpoint
     */
    @POST("api/v1/Auth/external-login")
    suspend fun postAuthDetails(@Body user: SsoResultBody): ResponseBody

    /**
     * Update profile picture API service
     */
    @Multipart
    @PATCH("api/v1/User/change-picture")
    suspend fun updateProfileImage(@Header("Authorization") token: String, @Part image: MultipartBody.Part): UpdateProfileImageResponse

    /**
     * Update profile details API service
     */
    @PATCH("api/v1/User/update-profile")
    suspend fun updateProfileDetails(@Header("Authorization") token: String, @Body updateProfileDetails: UpdateProfileDetails):
            Response<Unit>
}

