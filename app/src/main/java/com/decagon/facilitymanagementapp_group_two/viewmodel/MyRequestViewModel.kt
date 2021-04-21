package com.decagon.facilitymanagementapp_group_two.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import com.decagon.facilitymanagementapp_group_two.model.repository.MyRequestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.lang.IllegalArgumentException
import javax.inject.Inject


class MyRequestViewModel (private val repository: MyRequestRepository) : ViewModel() {

    private var currentUserId: String? = null
    private var currentSearchResult: Flow<PagingData<Complaints>>? = null

    fun searchMyRequest(userId: String): Flow<PagingData<Complaints>> {
        val lastResult = currentSearchResult
        if (userId == currentUserId && lastResult != null) {
            return lastResult
        }
        currentUserId = userId
        val newResult: Flow<PagingData<Complaints>> = repository.getMyRequests(userId)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}

class ViewModelFactory(private val repository: MyRequestRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyRequestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyRequestViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}