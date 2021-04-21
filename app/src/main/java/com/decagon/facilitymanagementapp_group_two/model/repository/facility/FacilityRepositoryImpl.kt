package com.decagon.facilitymanagementapp_group_two.model.repository.facility

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.decagon.facilitymanagementapp_group_two.model.data.CommentResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Comment
import com.decagon.facilitymanagementapp_group_two.model.data.RequestResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.database.CentralDatabase
import com.decagon.facilitymanagementapp_group_two.model.data.entities.ComplaintItems
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.network.ApiService
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
import com.decagon.facilitymanagementapp_group_two.network.safeApiCall
import com.decagon.facilitymanagementapp_group_two.utils.USER_ID

/**
 * This repository class abstracts access to the API endpoint, provides a clean API for
 * for managing requests, network calls and database calls within the facility management application
 */
class FacilityRepositoryImpl(
    private val apiService: ApiService,
    private val centralDatabase: CentralDatabase,
    private val sharedPref: SharedPreferences
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

    override suspend fun postNewComment(complaintId: String, comment: String) : ResultStatus<CommentResponseBody> {
       return safeApiCall { apiService.postNewComment(complaintId, comment) }
    }


    override suspend fun getComplaints(feedId: String, page: Int): ResultStatus<ComplaintItems> {
        return safeApiCall { apiService.getComplaints(feedId, page) }
    }

    override suspend fun getMyComplains(page: Int): ResultStatus<ComplaintItems> {
        return safeApiCall { apiService.getMyComplains(sharedPref
            .getString(USER_ID, null)!!, page) }
    }

    override suspend fun saveComplaints(complaints: List<Complaints>) {
        centralDatabase.complaintsDao.saveComplaints(complaints)
    }

    override suspend fun saveComplainsAsRequest(complains: List<Complaints>) {
        complains.forEach { complain ->
            val request = Request(
                title = complain.subject,
                type = complain.category,
                question = complain.description,
                image = complain.complaintImgUrl,
                userId = sharedPref.getString(USER_ID, null),
                id = complain.id
            )
            addNewRequestToDb(request)
        }
    }

    override fun getComplaintsFromDb(cat: String): LiveData<List<Complaints>?> {
        return centralDatabase.complaintsDao.getComplaintsByCat(cat)
    }

    override fun getMyRequestFromDb(): LiveData<List<Request>?> {
        return centralDatabase.requestDao.getAllRequest()
    }

    override fun getFeedIdByName(name: String): LiveData<String> {
        return centralDatabase.feedDao.getFeedIdByName(name)
    }

    override suspend fun getRequestById(id: String): ResultStatus<RequestResponseBody> {
        return safeApiCall { apiService.getRequestById(id) }
    }

    override fun getCommentsFromDb(id: String): LiveData<Request>{
       return centralDatabase.requestDao.getCommentById(id)
    }


}