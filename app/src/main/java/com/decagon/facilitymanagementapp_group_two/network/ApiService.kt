package com.decagon.facilitymanagementapp_group_two.network

import com.decagon.facilitymanagementapp_group_two.data.Comment
import com.decagon.facilitymanagementapp_group_two.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.data.entities.User
import com.decagon.facilitymanagementapp_group_two.model.ResponseBody
import com.decagon.facilitymanagementapp_group_two.model.SsoResultBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
    suspend fun postAuthDetails(@Body user : SsoResultBody) : ResponseBody
}
