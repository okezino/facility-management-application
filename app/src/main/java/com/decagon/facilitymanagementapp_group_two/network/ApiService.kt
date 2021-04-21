package com.decagon.facilitymanagementapp_group_two.network

import com.decagon.facilitymanagementapp_group_two.model.data.*
import com.decagon.facilitymanagementapp_group_two.model.data.entities.*
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

    @GET("api/v1/User/get-user/{userId}")
    suspend fun getUser(
        @Path("userId") id: String
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
    suspend fun postAuthDetails(@Header("bearer") token : String): ResponseBody

    /**
     * Update profile picture API service
     */
    @Multipart
    @PATCH("api/v1/User/change-picture")
    suspend fun updateProfileImage(@Part image: MultipartBody.Part): UpdateProfileImageResponse

    /**
     * Update profile details API service
     */
    @PATCH("api/v1/User/update-profile")
    suspend fun updateProfileDetails(@Body updateProfileDetails: UpdateProfileDetails): Response<Unit>

    @GET("/api/v1/Feed/{feedId}/get-complaints/{pageNumber}")
    suspend fun getComplaints(
        @Path("feedId") feedId: String,
        @Path("pageNumber") page: Int
    ): ComplaintItems

    @GET("/api/v1/Feed/get-user-complaints/{userId}/{pageNumber}")
    suspend fun getMyComplains(
        @Path("userId") userId: String,
        @Path("pageNumber") page: Int
    ): ComplaintItems

    /**
     * Posts new request to endpoint and retrieves the response body containing the request
     */
    @POST("/api/v1/Feed/{feedId}/add-complaint")
    suspend fun postNewRequest(@Path("feedId") feedId : String, @Body request: Request) : RequestResponseBody

    /**
     * Gets all the feeds category from the endpoint
     */
    @GET("api/v1/Feed/get-feeds/1")
    suspend fun getAllFeeds()  : FeedResponseBody

    /**
     * Post a comment to an existing complaint and associates it to a User
     */
    @POST("api/v1/Feed/complaint/{complaintId}/add-comment")
    suspend fun postNewComment(@Path("complaintId") complaintId : String, @Body comment: String)

}
