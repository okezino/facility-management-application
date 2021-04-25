package com.decagon.facilitymanagementapp_group_two.model.mediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.decagon.facilitymanagementapp_group_two.model.data.database.CentralDatabase
import com.decagon.facilitymanagementapp_group_two.model.data.entities.ApplianceComplaints
import com.decagon.facilitymanagementapp_group_two.model.data.entities.ApplianceRemoteKeys
import com.decagon.facilitymanagementapp_group_two.model.data.entities.OthersComplaints
import com.decagon.facilitymanagementapp_group_two.model.data.entities.OthersRemoteKeys
import com.decagon.facilitymanagementapp_group_two.network.ApiService
import com.decagon.facilitymanagementapp_group_two.utils.NETWORK_STARTING_PAGE
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class OthersRemoteMediator(
    private var feedId: String,
    private val apiService: ApiService,
    private val centralDatabase: CentralDatabase
) : RemoteMediator<Int, OthersComplaints>() {

    private var key: Long? = null

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, OthersComplaints>,
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
            val apiResponse = apiService.getComplaints(feedId, page)
            val complains = apiResponse.data?.items
            key = apiResponse.data?.currentPage
            val othersComplaints = complains?.map { complain ->
                OthersComplaints(
                    subject = complain.subject,
                    category = complain.category,
                    description = complain.description,
                    complaintImgUrl = complain.complaintImgUrl,
                    userFirstName = complain.userFirstName,
                    id = complain.id,
                    userLastName = complain.userLastName,
                    userImgUrl = complain.userImgUrl,
                    userSquad = complain.userSquad
                )
            }
            val endOfPaginationReached = apiResponse.data?.currentPage == apiResponse.data?.totalNumberOfPages
            centralDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    centralDatabase.othersRemoteKeysDao.clearRemoteKeys()
                    centralDatabase.othersComplainsDao.clearComplains()
                }
                val prevKey = if (page == NETWORK_STARTING_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = complains?.map {
                    OthersRemoteKeys(id = key, prevKey = prevKey, nextKey = nextKey)
                }
                centralDatabase.othersRemoteKeysDao.insertAll(keys)
                centralDatabase.othersComplainsDao.saveComplaints(othersComplaints)

            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, OthersComplaints>): OthersRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                Log.d("PAGE", "${centralDatabase.othersRemoteKeysDao.remoteKeysId(key)}")
                // Get the remote keys of the last item retrieved
                centralDatabase.othersRemoteKeysDao.remoteKeysId(key)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, OthersComplaints>): OthersRemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                Log.d("PAGE", "${centralDatabase.othersRemoteKeysDao.remoteKeysId(key)}")
                // Get the remote keys of the first items retrieved
                centralDatabase.othersRemoteKeysDao.remoteKeysId(key)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, OthersComplaints>
    ): OthersRemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let {
                Log.d("PAGE", "${centralDatabase.othersRemoteKeysDao.remoteKeysId(key)}")
                centralDatabase.othersRemoteKeysDao.remoteKeysId(key)
            }
        }
    }
}