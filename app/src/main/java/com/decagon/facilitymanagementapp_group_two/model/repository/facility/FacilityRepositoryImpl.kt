package com.decagon.facilitymanagementapp_group_two.model.repository.facility

import com.decagon.facilitymanagementapp_group_two.model.data.database.RequestDao
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.network.ApiService

class FacilityRepositoryImpl(
    private val apiService: ApiService,
    private val requestDao: RequestDao
) : FacilityRepository {


    override suspend fun postRequest(feedId : String, request: Request) {
        apiService.postNewRequest(feedId, request)
    }

    override suspend fun addNewRequestToDb(request: Request) {
        requestDao.insert(request)
    }

    override suspend fun getFeedId(requestCategory : String) : String{
        return apiService.getFeedId(requestCategory)
    }
}