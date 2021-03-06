package com.decagon.facilitymanagementapp_group_two.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.facilitymanagementapp_group_two.model.data.*
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.model.repository.facility.FacilityRepository
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleComplaintViewModel @Inject constructor(
    private val facilityRepository: FacilityRepository
) : ViewModel() {

    private var _likesCount = MutableLiveData<Int>()
    val likesCount : LiveData<Int>
    get() = _likesCount

    fun getRequestById(id: String): LiveData<ResultStatus<RequestResponseBody>> {
        val response = MutableLiveData<ResultStatus<RequestResponseBody>>()
        viewModelScope.launch {
            response.value = facilityRepository.getRequestById(id)
        }
        return response
    }
    fun postRating(complaintId: String, rating: RatingBody) : LiveData<ResultStatus<RatingResponseBody>>{
        val response = MutableLiveData<ResultStatus<RatingResponseBody>>()
        viewModelScope.launch {
            response.value = facilityRepository.postRating(complaintId, rating)
        }
        return response
    }

    fun deleteRating(ratingId: String?) : LiveData<ResultStatus<RatingResponseBody>>{
        val response = MutableLiveData<ResultStatus<RatingResponseBody>>()
        viewModelScope.launch {
            response.value = ratingId?.let { facilityRepository.deleteRating(it) }
        }
        return response
    }

    fun reduceRatingCount(request : Request, likesCount: Int){
        viewModelScope.launch {
            facilityRepository.addNewRequestToDb(request)
            _likesCount.value = likesCount - 1

        }
    }

    fun saveRequestToDb(request : Request, likesCount : Int){
        viewModelScope.launch {
            facilityRepository.addNewRequestToDb(request)
            _likesCount.value = likesCount + 1

        }
    }

    fun getRatingId(complaintId: String) : LiveData<String>{
        val response = MutableLiveData<String>()
        viewModelScope.launch {
           response.value =  facilityRepository.getRatingIdFromDb(complaintId)
        }
        return response
    }

    fun addRatingData(rating : RatingData?){
        viewModelScope.launch {
            facilityRepository.addRatingData(rating)
        }
    }


}

