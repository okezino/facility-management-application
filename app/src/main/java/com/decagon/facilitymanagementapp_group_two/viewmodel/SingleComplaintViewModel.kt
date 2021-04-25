package com.decagon.facilitymanagementapp_group_two.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.facilitymanagementapp_group_two.model.data.CommentResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.RequestResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Comment
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.model.data.entities.UserData
import com.decagon.facilitymanagementapp_group_two.model.repository.facility.FacilityRepository
import com.decagon.facilitymanagementapp_group_two.network.ApiResponseHandler
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleComplaintViewModel @Inject constructor(
    private val facilityRepository: FacilityRepository
) : ViewModel() {

    fun postNewComment(complaintId : String, comment : String) : LiveData<ResultStatus<CommentResponseBody>>{
        val response = MutableLiveData<ResultStatus<CommentResponseBody>>()
        viewModelScope.launch {
          response.value =   facilityRepository.postNewComment(complaintId, comment)
        }
        return response
    }

    fun getRequestById(id : String) : LiveData<ResultStatus<RequestResponseBody>>{
        val response = MutableLiveData<ResultStatus<RequestResponseBody>>()
        viewModelScope.launch {
          response.value = facilityRepository.getRequestById(id)
        }
        return response
    }

    fun getRequestFromDb(id : String) : LiveData<Request>{
       return facilityRepository.getCommentsFromDb(id)
    }




}