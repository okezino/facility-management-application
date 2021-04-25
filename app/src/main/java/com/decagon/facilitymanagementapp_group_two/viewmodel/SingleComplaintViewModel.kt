package com.decagon.facilitymanagementapp_group_two.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.facilitymanagementapp_group_two.model.data.CommentResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.RatingBody
import com.decagon.facilitymanagementapp_group_two.model.data.RatingResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.RequestResponseBody
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

    private var _isLiked = MutableLiveData<Boolean>()
    val isLiked : LiveData<Boolean>
        get() = _isLiked

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

    fun postRating(complaintId: String, rating: RatingBody) : LiveData<ResultStatus<RatingResponseBody>>{
        val response = MutableLiveData<ResultStatus<RatingResponseBody>>()
        viewModelScope.launch {
            response.value = facilityRepository.postRating(complaintId, rating)
        }
        return response
    }

    fun deleteRating(complaintId: String) : LiveData<ResultStatus<RatingResponseBody>>{
        val response = MutableLiveData<ResultStatus<RatingResponseBody>>()
        viewModelScope.launch {
            val ratingId = facilityRepository.getRequestRatingIdFromDb(complaintId)
            response.value = facilityRepository.deleteRating(ratingId)
        }
        return response
    }

    fun reduceRatingCount(request : Request, likesCount: Int){
        viewModelScope.launch {
            facilityRepository.addNewRequestToDb(request)
            _likesCount.value = likesCount - 1

        }
    }


    fun getRequestFromDb(id : String) : LiveData<Request>{
       return facilityRepository.getCommentsFromDb(id)
    }

    fun saveRequestToDb(request : Request, likesCount : Int){
        viewModelScope.launch {
            facilityRepository.addNewRequestToDb(request)
            _likesCount.value = likesCount + 1

        }
    }

    fun getIsLikedFromDb(complaintId: String){
        viewModelScope.launch {
            _isLiked = facilityRepository.getIsLikedFromDb(complaintId) as MutableLiveData<Boolean>
        }
    }





}