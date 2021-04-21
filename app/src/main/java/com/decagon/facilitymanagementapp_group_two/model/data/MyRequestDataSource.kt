package com.decagon.facilitymanagementapp_group_two.model.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import com.decagon.facilitymanagementapp_group_two.network.ApiService
import com.decagon.facilitymanagementapp_group_two.utils.NETWORK_PAGE_SIZE
import com.decagon.facilitymanagementapp_group_two.utils.NETWORK_STARTING_PAGE
import retrofit2.HttpException
import java.io.IOException

class MyRequestDataSource(
    private val apiService: ApiService,
    private val userId: String
) : PagingSource<Int, Complaints>() {

    override fun getRefreshKey(state: PagingState<Int, Complaints>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Complaints> {
        val position = params.key ?: NETWORK_STARTING_PAGE
        return try {
            val response = apiService.getMyComplains(userId, position)
            val requests = response.data.items
            val nextKey = if (requests.isEmpty()) {
                null
            } else {
                position + 1 //(params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = requests,
                prevKey = if (position == NETWORK_STARTING_PAGE) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}