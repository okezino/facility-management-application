package com.decagon.facilitymanagementapp_group_two.model.mediator

import android.content.SharedPreferences
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.decagon.facilitymanagementapp_group_two.model.data.database.CentralDatabase
import com.decagon.facilitymanagementapp_group_two.model.data.entities.ComplaintItems
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import com.decagon.facilitymanagementapp_group_two.model.data.entities.RemoteKeys
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.network.ApiService
import com.decagon.facilitymanagementapp_group_two.utils.NETWORK_STARTING_PAGE
import com.decagon.facilitymanagementapp_group_two.utils.USER_ID
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class RequestRemoteMediator(
    private val apiService: ApiService,
    private val centralDatabase: CentralDatabase,
    sharedPref: SharedPreferences
) : RemoteMediator<Int, Request>() {

    private val userId = sharedPref.getString(USER_ID, null)!!
    private var key: Long? = null

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Request>,
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: NETWORK_STARTING_PAGE
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse = apiService.getMyComplains(userId, page)
            val complains = apiResponse.data?.items
            key = apiResponse.data?.currentPage
            val requests = complains?.map { complain ->
                Request(
                    title = complain.subject,
                    type = complain.category,
                    question = complain.description,
                    image = complain.complaintImgUrl,
                    userId = userId,
                    id = complain.id
                )
            }
            val endOfPaginationReached = apiResponse.data == null
            centralDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    centralDatabase.remoteKeysDao.clearRemoteKeys()
                    centralDatabase.requestDao.clearRequests()
                }
                val prevKey = if (page == NETWORK_STARTING_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = requests?.map {
                    RemoteKeys(id = key, prevKey = prevKey, nextKey = nextKey)
                }
                centralDatabase.remoteKeysDao.insertAll(keys)
                centralDatabase.requestDao.insertAll(requests)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Request>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                //    Log.d("PAGE", "${centralDatabase.remoteKeysDao.remoteKeysId(key)}")
                // Get the remote keys of the last item retrieved
                centralDatabase.remoteKeysDao.remoteKeysId(key)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Request>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                //  Log.d("PAGE", "${centralDatabase.remoteKeysDao.remoteKeysId(key)}")
                // Get the remote keys of the first items retrieved
                centralDatabase.remoteKeysDao.remoteKeysId(key)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Request>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let {
                Log.d("PAGE", "${centralDatabase.remoteKeysDao.remoteKeysId(key)}")
                centralDatabase.remoteKeysDao.remoteKeysId(key)
            }
        }
    }
}