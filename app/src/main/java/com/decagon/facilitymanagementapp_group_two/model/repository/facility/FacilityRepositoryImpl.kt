package com.decagon.facilitymanagementapp_group_two.model.repository.facility

import com.decagon.facilitymanagementapp_group_two.model.data.RequestResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.database.CentralDatabase
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.network.ApiService
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
import com.decagon.facilitymanagementapp_group_two.network.safeApiCall

/**
 * This repository class abstracts access to the API endpoint, provides a clean API for
 * for managing requests, network calls and database calls within the facility management application
 */
class FacilityRepositoryImpl(
    private val apiService: ApiService,
    private val centralDatabase: CentralDatabase
) : FacilityRepository {

    override suspend fun postRequest(feedId : String, request: Request) : ResultStatus<RequestResponseBody> {
       return safeApiCall { apiService.postNewRequest(feedId, request) }
    }

    override suspend fun addNewRequestToDb(request: Request) {
        centralDatabase.requestDao.insert(request)
    }

    override suspend fun getFeedId(requestCategory : String) : String{
        return centralDatabase.feedDao.getFeedId(requestCategory)
    }

    override suspend fun postNewComment(complaintId: String, comment: String) {
        TODO("Not yet implemented")
    }
}