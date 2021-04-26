package com.decagon.facilitymanagementapp_group_two.model.repository.facility

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.decagon.facilitymanagementapp_group_two.model.data.*
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Comment

import androidx.paging.*
import com.decagon.facilitymanagementapp_group_two.model.data.CommentResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.DeleteResponse
import com.decagon.facilitymanagementapp_group_two.model.data.RequestResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.database.CentralDatabase
import com.decagon.facilitymanagementapp_group_two.model.data.entities.*
import com.decagon.facilitymanagementapp_group_two.model.mediator.*
import com.decagon.facilitymanagementapp_group_two.network.ApiService
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
import com.decagon.facilitymanagementapp_group_two.network.safeApiCall
import com.decagon.facilitymanagementapp_group_two.utils.*
import kotlinx.coroutines.flow.Flow

/**
 * This repository class abstracts access to the API endpoint, provides a clean API for
 * for managing requests, network calls and database calls within the facility management application
 */
class FacilityRepositoryImpl(
    private val apiService: ApiService,
    private val centralDatabase: CentralDatabase,
    private val sharedPref: SharedPreferences
) : FacilityRepository {

    override suspend fun postRequest(feedId : String, request: RequestBody) : ResultStatus<RequestResponseBody> {
       return safeApiCall { apiService.postNewRequest(feedId, request) }
    }

    override suspend fun addNewRequestToDb(request: Request) {
        centralDatabase.requestDao.insert(request)
    }

    override suspend fun getFeedId(requestCategory: String): String {
        return centralDatabase.feedDao.getFeedId(requestCategory)
    }

    override suspend fun postNewComment(complaintId: String, comment: String): ResultStatus<CommentResponseBody> {
        return safeApiCall { apiService.postNewComment(complaintId, comment) }
    }

    override suspend fun getComplaints(feedId: String, page: Int): ResultStatus<ComplaintItems> {
        return safeApiCall { apiService.getComplaints(feedId, page) }
    }

    override suspend fun getMyComplains(page: Int): ResultStatus<ComplaintItems> {
        return safeApiCall {
            apiService.getMyComplains(
                sharedPref
                    .getString(USER_ID, null)!!,
                page
            )
        }
    }

    override suspend fun saveComplaints(complaints: List<Complaints>) {
        centralDatabase.complaintsDao.saveComplaints(complaints)
    }

    override suspend fun saveComplainsAsRequest(complains: List<Complaints>) {
       val request = complains.map { complain ->
            Request(
                title = complain.subject,
                type = complain.category,
                question = complain.description,
                image = complain.complaintImgUrl,
                userId = sharedPref.getString(USER_ID, null),
                id = complain.id
            )
        }
        centralDatabase.requestDao.insertAll(request)
    }

    override fun getMyRequestFromDb(): LiveData<List<Request>?> {
        return centralDatabase.requestDao.getAllRequest()
    }

    override fun getFeedIdByName(name: String): LiveData<String> {
        return centralDatabase.feedDao.getFeedIdByName(name)
    }

    override suspend fun deleteComplaint(complainId: String): ResultStatus<DeleteResponse> {

        return safeApiCall { apiService.deleteRequest(complainId) }
    }

    override suspend fun deleteComplaintFromDataBase(request: Request) {
        centralDatabase.requestDao.deleteRequest(request)
    }

    // Paging
    override fun getMyRequest(): Flow<PagingData<Request>> {
        val pagingSourceFactory = { centralDatabase.requestDao.getMyRequests() }
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = RequestRemoteMediator(
                apiService,
                centralDatabase,
                sharedPref
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun getComplainsByFeed(): Flow<PagingData<Complaints>> {
        val pagingSourceFactory = { centralDatabase.complaintsDao.getComplaintsByCat(FOOD) }
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = ComplainsRemoteMediator(
                sharedPref.getString(FOOD, null)!!,
                apiService,
                centralDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun getApartComplains(): Flow<PagingData<ApartComplaints>> {
        val pagingSourceFactory = { centralDatabase.apartComplainsDao.getComplaintsByCat(APARTMENT) }
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = ApartmentRemoteMediator(
                sharedPref.getString(APARTMENT, null)!!,
                apiService,
                centralDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun postRating(complaintId: String, rating: RatingBody): ResultStatus<RatingResponseBody> {
        return safeApiCall { apiService.addRating(complaintId, rating) }
    }

    override suspend fun deleteRating(ratingId: String): ResultStatus<RatingResponseBody> {
        return safeApiCall { apiService.deleteRating(ratingId) }
    }

    override suspend fun getRequestRatingIdFromDb(complaintId: String): String {
        return centralDatabase.requestDao.getRequestRatingId(complaintId)
    }

    override fun getIsLikedFromDb(complaintId: String): LiveData<Boolean> {
        return centralDatabase.requestDao.getIsLikedFromDb(complaintId)
    }

    override fun getApplianceComplains(): Flow<PagingData<ApplianceComplaints>> {
        val pagingSourceFactory = { centralDatabase.applianceCompDao.getComplaintsByCat(APPLIANCE) }
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = ApplianceRemoteMediator(
                sharedPref.getString(APPLIANCE, null)!!,
                apiService, centralDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun getOthersComplains(): Flow<PagingData<OthersComplaints>> {
        val pagingSourceFactory = { centralDatabase.othersComplainsDao.getComplaintsByCat(OTHERS) }
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = OthersRemoteMediator(
                sharedPref.getString(OTHERS, null)!!,
                apiService, centralDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun getRequestById(id: String): ResultStatus<RequestResponseBody> {
        return safeApiCall { apiService.getRequestById(id) }
    }

    override fun getCommentsFromDb(id: String): LiveData<Request> {
        return centralDatabase.requestDao.getCommentById(id)
    }
}
