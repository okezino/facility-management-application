package com.decagon.facilitymanagementapp_group_two.model.repository.facility

import com.decagon.facilitymanagementapp_group_two.model.data.RequestResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus

/**
 * Contain declaration of an abstract method which is implemented by the  facilityRepository
 * class
 */
interface FacilityRepository {

    suspend fun postRequest(feedId : String, request : Request) : ResultStatus<RequestResponseBody>

    suspend fun addNewRequestToDb(request: Request)

    suspend fun getFeedId(requestCategory: String) : String

    suspend fun postNewComment(complaintId : String, comment : String)
}