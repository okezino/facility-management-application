package com.decagon.facilitymanagementapp_group_two.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.model.repository.facility.FacilityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FeedsViewModel @Inject constructor(private val fRepository: FacilityRepository) : ViewModel() {

    /**
     * hold a reference to my request for fast search operation
     */
    private var currentSearchResult: Flow<PagingData<Request>>? = null

    /**
     * Method to get my requests/complains
     */
    fun getMyRequests(): Flow<PagingData<Request>> {
        val lastResult = currentSearchResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<Request>> = fRepository.getMyRequest()
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    /**
     * Method to get request/complains under food category
     */
    fun getComplainsById(): Flow<PagingData<Complaints>> {
        return fRepository.getComplainsByFeed()
            .cachedIn(viewModelScope)
    }

    /**
     * Method to get request/complains under apartment category
     */
    fun getApartComplains(): Flow<PagingData<Complaints>> {
        val apartComplaints = fRepository.getApartComplains()
        return apartComplaints.map {
            it.map { complain ->
                Complaints(
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
        }
            .cachedIn(viewModelScope)
    }

    /**
     * Method to get request/complains under appliances category
     */
    fun getAppComplains(): Flow<PagingData<Complaints>> {
        val appComplaints = fRepository.getApplianceComplains()
        return appComplaints.map {
            it.map { complain ->
                Complaints(
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
        }
            .cachedIn(viewModelScope)
    }

    /**
     * Method to get request/complains under others category
     */
    fun getOtherComplains(): Flow<PagingData<Complaints>> {
        val appComplaints = fRepository.getOthersComplains()
        return appComplaints.map {
            it.map { complain ->
                Complaints(
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
        }
            .cachedIn(viewModelScope)
    }

    /**
     * Method to search for request based on words entered by the user
     */
    fun searchMyRequest(searchEntry: String): Flow<PagingData<Request>>? {
        return currentSearchResult?.map {
            it.filter { request ->
                request.title?.contains(searchEntry, ignoreCase = true) == true ||
                        request.question?.contains(searchEntry, ignoreCase = true) == true
            }
        }
    }
}