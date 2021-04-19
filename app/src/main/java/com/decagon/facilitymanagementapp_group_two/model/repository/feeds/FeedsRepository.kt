package com.decagon.facilitymanagementapp_group_two.model.repository.feeds

import androidx.lifecycle.LiveData
import com.decagon.facilitymanagementapp_group_two.model.data.entities.ComplaintItems
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import com.decagon.facilitymanagementapp_group_two.model.data.entities.FeedItems
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Feeds
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus

interface FeedsRepository {
    suspend fun getFeedCategories(): ResultStatus<FeedItems>

    suspend fun saveFeeds(items: List<Feeds>)

    fun getFeedsFromDb(): LiveData<List<Feeds>>

    suspend fun getComplaints(feedId: String, page: Int): ResultStatus<ComplaintItems>

    suspend fun saveComplaints(complaints: List<Complaints>)

    fun getComplaintsFromDb(cat: String): LiveData<List<Complaints>?>

    fun getMyComplaintsFromDb(): LiveData<List<Complaints>?>

    suspend fun saveFeedId(key: String, value: String)
}