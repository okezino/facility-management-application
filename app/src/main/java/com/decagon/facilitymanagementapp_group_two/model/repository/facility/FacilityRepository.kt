package com.decagon.facilitymanagementapp_group_two.model.repository.facility

import androidx.lifecycle.LiveData
import com.decagon.facilitymanagementapp_group_two.model.data.RequestResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.entities.ComplaintItems
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
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


    suspend fun getComplaints(feedId: String, page: Int): ResultStatus<ComplaintItems>

    suspend fun getMyComplains(page: Int): ResultStatus<ComplaintItems>

    suspend fun saveComplaints(complaints: List<Complaints>)

    suspend fun saveComplainsAsRequest(complains: List<Complaints>)

    fun getComplaintsFromDb(cat: String): LiveData<List<Complaints>?>

    fun getMyRequestFromDb(): LiveData<List<Request>?>

    fun getFeedIdByName(name: String): LiveData<String>
}