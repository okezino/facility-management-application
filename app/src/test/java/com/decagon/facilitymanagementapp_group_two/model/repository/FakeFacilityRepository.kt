package com.decagon.facilitymanagementapp_group_two.model.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.decagon.facilitymanagementapp_group_two.model.data.*
import com.decagon.facilitymanagementapp_group_two.model.data.entities.*
import com.decagon.facilitymanagementapp_group_two.model.repository.facility.FacilityRepository
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
import kotlinx.coroutines.flow.Flow

class FakeFacilityRepository : FacilityRepository {
    private val requests = mutableListOf<Request>()
    private var shouldReturnNetworkError = true

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun postRequest(
        feedId: String,
        request: RequestBody,
    ): ResultStatus<RequestResponseBody> {
        return if (shouldReturnNetworkError) {
            ResultStatus.NetworkError
        } else {
            ResultStatus.Success(RequestResponseBody(Request(time = ""), "Post Request Successful", true))
        }
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

    override fun getMyRequest(): Flow<PagingData<Request>> {
        TODO("Not yet implemented")
    }

    override fun getComplainsByFeed(): Flow<PagingData<Complaints>> {
        TODO("Not yet implemented")
    }

    override fun getApartComplains(): Flow<PagingData<ApartComplaints>> {
        TODO("Not yet implemented")
    }

    override fun getApplianceComplains(): Flow<PagingData<ApplianceComplaints>> {
        TODO("Not yet implemented")
    }

    override fun getOthersComplains(): Flow<PagingData<OthersComplaints>> {
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

    override suspend fun postRating(
        complaintId: String,
        rating: RatingBody,
    ): ResultStatus<RatingResponseBody> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRating(ratingId: String): ResultStatus<RatingResponseBody> {
        TODO("Not yet implemented")
    }

    override suspend fun getRequestRatingIdFromDb(complaintId: String): String {
        TODO("Not yet implemented")
    }

    override fun getIsLikedFromDb(complaintId: String): LiveData<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getRatingIdFromDb(complaintId: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun addRatingData(rating: RatingData?) {
        TODO("Not yet implemented")
    }
}
