package com.decagon.facilitymanagementapp_group_two.model.repository

import com.decagon.facilitymanagementapp_group_two.model.data.RequestResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.model.repository.facility.FacilityRepository
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus


class FakeFacilityRepository : FacilityRepository {
    private val requests = mutableListOf<Request>()
    private var shouldReturnNetworkError = true
    override suspend fun postRequest(
        feedId: String,
        request: Request,
    ): ResultStatus<RequestResponseBody> {
        return if (shouldReturnNetworkError){
            ResultStatus.NetworkError
        }else{
            ResultStatus.Success(RequestResponseBody(Request(),"Post Request Successful",true))
        }
    }

    fun setShouldReturnNetworkError(value : Boolean){
        shouldReturnNetworkError = value
    }

    override suspend fun addNewRequestToDb(request: Request) {
        requests.add(request)
    }

    override suspend fun getFeedId(requestCategory: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun postNewComment(complaintId: String, comment: String) {

    }
}