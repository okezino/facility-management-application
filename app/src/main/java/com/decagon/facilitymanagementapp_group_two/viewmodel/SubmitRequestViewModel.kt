package com.decagon.facilitymanagementapp_group_two.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.model.repository.facility.FacilityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubmitRequestViewModel @Inject constructor(
    private val facilityRepository: FacilityRepository
) : ViewModel() {

    private var _feedId = MutableLiveData<String>()
    val feedId : LiveData<String>
    get() = _feedId



    fun postNewFeed(feedId : String, request : Request){
        viewModelScope.launch {
            facilityRepository.postRequest(feedId, request)
            facilityRepository.addNewRequestToDb(request)
        }
    }

    fun getFeedId(requestCategory: String){
        viewModelScope.launch {
           _feedId.value = facilityRepository.getFeedId(requestCategory)
        }
    }


}