package com.decagon.facilitymanagementapp_group_two.model.repository.feeds

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.decagon.facilitymanagementapp_group_two.model.data.database.CentralDatabase
import com.decagon.facilitymanagementapp_group_two.model.data.entities.ComplaintItems
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import com.decagon.facilitymanagementapp_group_two.model.data.entities.FeedItems
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Feeds
import com.decagon.facilitymanagementapp_group_two.network.ApiService
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
import com.decagon.facilitymanagementapp_group_two.network.safeApiCall
import com.decagon.facilitymanagementapp_group_two.utils.FIRST_NAME
import com.decagon.facilitymanagementapp_group_two.utils.LAST_NAME

class FeedsRepositoryImpl(
    private val apiService: ApiService,
    private val db: CentralDatabase, private val sharedPreferences: SharedPreferences
) : FeedsRepository {
    override suspend fun getFeedCategories(): ResultStatus<FeedItems> {
        return safeApiCall { apiService.getFeedsCategories() }
    }

    override suspend fun saveFeeds(items: List<Feeds>) {
        db.feedsDao.saveFeeds(items)
    }

    override fun getFeedsFromDb(): LiveData<List<Feeds>> {
        return db.feedsDao.getFeeds()
    }

    override suspend fun getComplaints(feedId: String, page: Int): ResultStatus<ComplaintItems> {
        return safeApiCall { apiService.getComplaints(feedId, page) }
    }

    override suspend fun saveComplaints(complaints: List<Complaints>) {
        db.complaintsDao.saveComplaints(complaints)
    }

    override fun getComplaintsFromDb(cat: String): LiveData<List<Complaints>?> {
        return db.complaintsDao.getComplaintsByCat(cat)
    }

    override fun getMyComplaintsFromDb(): LiveData<List<Complaints>?> {
        return db.complaintsDao.getMyComplaints("Godday", "Okoduwa")
    }

    override suspend fun saveFeedId(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }
}