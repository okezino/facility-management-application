package com.decagon.facilitymanagementapp_group_two.model.repository.facility

import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request

interface FacilityRepository {

    suspend fun postRequest(feedId : String, request : Request)

    suspend fun addNewRequestToDb(request: Request)

    suspend fun getFeedId(requestCategory: String) : String
}