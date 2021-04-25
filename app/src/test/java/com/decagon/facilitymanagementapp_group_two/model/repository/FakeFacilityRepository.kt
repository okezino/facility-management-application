package com.decagon.facilitymanagementapp_group_two.model.repository

import androidx.lifecycle.LiveData
import com.decagon.facilitymanagementapp_group_two.model.data.CommentResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.DeleteResponse
import com.decagon.facilitymanagementapp_group_two.model.data.RequestResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.entities.ComplaintItems
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
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
        return if (shouldReturnNetworkError) {
            ResultStatus.NetworkError
        } else {
            ResultStatus.Success(RequestResponseBody(Request(), "Post Request Successful", true))
        }
    }

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun addNewRequestToDb(request: Request) {
        requests.add(request)
    }

    override suspend fun getFeedId(requestCategory: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun postNewComment(complaintId: String, comment: String): ResultStatus<CommentResponseBody> {
        TODO("Not yet implemented")
    }

    override suspend fun getComplaints(feedId: String, page: Int): ResultStatus<ComplaintItems> {
        TODO("Not yet implemented")
    }

    override suspend fun getMyComplains(page: Int): ResultStatus<ComplaintItems> {
        TODO("Not yet implemented")
    }

    override suspend fun saveComplaints(complaints: List<Complaints>) {
        TODO("Not yet implemented")
    }

    override suspend fun saveComplainsAsRequest(complains: List<Complaints>) {
        TODO("Not yet implemented")
    }

    override fun getMyRequestFromDb(): LiveData<List<Request>?> {
        TODO("Not yet implemented")
    }

    override fun getFeedIdByName(name: String): LiveData<String> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteComplaint(complainId: String): ResultStatus<DeleteResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteComplaintFromDataBase(request: Request) {
        TODO("Not yet implemented")
    }

    override suspend fun getRequestById(id: String): ResultStatus<RequestResponseBody> {
        TODO("Not yet implemented")
    }

    override fun getCommentsFromDb(id: String): LiveData<Request> {
        TODO("Not yet implemented")
    }
}
