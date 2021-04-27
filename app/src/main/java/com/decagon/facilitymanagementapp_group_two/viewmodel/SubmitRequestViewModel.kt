package com.decagon.facilitymanagementapp_group_two.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.facilitymanagementapp_group_two.model.data.RequestBody
import com.decagon.facilitymanagementapp_group_two.model.data.RequestResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.model.repository.facility.FacilityRepository
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This submitRequestViewModel handles getting feedId and posting new feed
 */
@HiltViewModel
class SubmitRequestViewModel @Inject constructor(
    private val facilityRepository: FacilityRepository
) : ViewModel() {

    private var _feedId = MutableLiveData<String>()
    val feedId: LiveData<String>
        get() = _feedId


    fun postNewRequest(feedId : String, request : RequestBody) : LiveData<ResultStatus<RequestResponseBody>>{
        val response = MutableLiveData<ResultStatus<RequestResponseBody>>()
        response.value = ResultStatus.Loading("Processing request..., please wait")
        viewModelScope.launch {
            response.value = facilityRepository.postRequest(feedId, request)
            // facilityRepository.addNewRequestToDb(request)
        }
        return response
    }

    fun getFeedId(requestCategory: String) {
        viewModelScope.launch {
            _feedId.value = facilityRepository.getFeedId(requestCategory)
        }
    }

    fun saveRequestToDb(request: Request) {
        viewModelScope.launch(Dispatchers.IO) {
            facilityRepository.addNewRequestToDb(request)
        }
    }
}
