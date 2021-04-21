package com.decagon.facilitymanagementapp_group_two.model.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.decagon.facilitymanagementapp_group_two.model.data.MyRequestDataSource
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import com.decagon.facilitymanagementapp_group_two.network.ApiService
import com.decagon.facilitymanagementapp_group_two.utils.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow

class MyRequestRepository(private val apiService: ApiService) {

    fun getMyRequests(userId: String): Flow<PagingData<Complaints>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MyRequestDataSource(apiService, userId) }
        ).flow
    }
}