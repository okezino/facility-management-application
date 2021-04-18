package com.decagon.facilitymanagementapp_group_two.model.repository.facility

import com.decagon.facilitymanagementapp_group_two.model.data.RequestResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus

interface FacilityRepository {

    suspend fun postRequest(feedId : String, request : Request) : ResultStatus<RequestResponseBody>

    suspend fun addNewRequestToDb(request: Request)

    suspend fun getFeedId(requestCategory: String) : String
}