package com.decagon.facilitymanagementapp_group_two.network

import com.decagon.facilitymanagementapp_group_two.data.Comment
import com.decagon.facilitymanagementapp_group_two.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.data.entities.User
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    /**
     * Get all comments from Api
     */
    @GET("comments")
    suspend fun getAllComment() : List<Comment>

    /**
     * @param id
     * get User data using the userId
     */

    @GET("user/{id}")
    suspend fun getUser(
        @Path("id") id : Int
    ) : User

    /**
     * Get all request from Api
     */

    @GET("request")
    suspend fun getAllRequest() : List<Request>



}