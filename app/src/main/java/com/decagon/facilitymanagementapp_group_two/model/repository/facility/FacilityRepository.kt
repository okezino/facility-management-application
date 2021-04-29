package com.decagon.facilitymanagementapp_group_two.model.repository.facility

import androidx.lifecycle.LiveData
import com.decagon.facilitymanagementapp_group_two.model.data.*
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Comment

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.decagon.facilitymanagementapp_group_two.model.data.CommentResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.DeleteResponse
import com.decagon.facilitymanagementapp_group_two.model.data.RequestResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.entities.*
import com.decagon.facilitymanagementapp_group_two.model.data.entities.ComplaintItems
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request

import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
import kotlinx.coroutines.flow.Flow

/**
 * Contain declaration of an abstract method which is implemented by the  facilityRepository
 * class
 */
interface FacilityRepository {

    suspend fun postRequest(feedId : String?, request : RequestBody) : ResultStatus<RequestResponseBody>

    suspend fun addNewRequestToDb(request: Request)

    suspend fun getFeedId(requestCategory: String): String

    suspend fun postNewComment(complaintId: String, comment: String): ResultStatus<CommentResponseBody>

    suspend fun getComplaints(feedId: String, page: Int): ResultStatus<ComplaintItems>

    suspend fun getMyComplains(page: Int): ResultStatus<ComplaintItems>

    suspend fun saveComplaints(complaints: List<Complaints>)

    suspend fun saveComplainsAsRequest(complains: List<Complaints>)

    fun getMyRequestFromDb(): LiveData<List<Request>?>

    fun getFeedIdByName(name: String): LiveData<String>

    suspend fun deleteComplaint(complainId: String): ResultStatus<DeleteResponse>

    fun getMyRequest(): Flow<PagingData<Request>>

    fun getComplainsByFeed(): Flow<PagingData<Complaints>>

    fun getApartComplains(): Flow<PagingData<ApartComplaints>>

    fun getApplianceComplains(): Flow<PagingData<ApplianceComplaints>>

    fun getOthersComplains(): Flow<PagingData<OthersComplaints>>

    suspend fun deleteComplaintFromDataBase(request: Request)

    suspend fun getRequestById(id: String): ResultStatus<RequestResponseBody>

    fun getCommentsFromDb(id : String) : LiveData<Request>

    suspend fun postRating(complaintId: String, rating : RatingBody) : ResultStatus<RatingResponseBody>

    suspend fun deleteRating(ratingId: String) : ResultStatus<RatingResponseBody>

    suspend fun getRequestRatingIdFromDb(complaintId: String) : String

    fun getIsLikedFromDb(complaintId: String): LiveData<Boolean>

    suspend fun getRatingIdFromDb(complaintId: String) : String

    suspend fun addRatingData(rating : RatingData?)

}


