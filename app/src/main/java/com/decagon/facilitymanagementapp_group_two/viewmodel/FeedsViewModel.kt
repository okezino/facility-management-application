package com.decagon.facilitymanagementapp_group_two.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.facilitymanagementapp_group_two.model.data.DeleteResponse
import com.decagon.facilitymanagementapp_group_two.model.data.entities.ComplaintItems
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.model.repository.facility.FacilityRepository
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
import com.decagon.facilitymanagementapp_group_two.utils.APARTMENT
import com.decagon.facilitymanagementapp_group_two.utils.APPLIANCE
import com.decagon.facilitymanagementapp_group_two.utils.FOOD
import com.decagon.facilitymanagementapp_group_two.utils.OTHERS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedsViewModel @Inject constructor(private val fRepository: FacilityRepository) : ViewModel() {

    val foodFeedId = fRepository.getFeedIdByName(FOOD)
    val apartFeedId = fRepository.getFeedIdByName(APARTMENT)
    val appFeedId = fRepository.getFeedIdByName(APPLIANCE)
    val otherFeedId = fRepository.getFeedIdByName(OTHERS)

    val foodComplaints = fRepository.getComplaintsFromDb(FOOD)
    val apartmentComplaints = fRepository.getComplaintsFromDb(APARTMENT)
    val appliancesComplaints = fRepository.getComplaintsFromDb(APPLIANCE)
    val otherComplaints = fRepository.getComplaintsFromDb(OTHERS)
    val myRequest = fRepository.getMyRequestFromDb()

    init {
        getMyComplains(1)
    }

    fun getComplaints(feedId: String, page: Int): LiveData<ResultStatus<ComplaintItems>> {
        val response = MutableLiveData<ResultStatus<ComplaintItems>>()
        viewModelScope.launch {
            response.value = fRepository.getComplaints(feedId, page)
        }
        return response
    }

    fun getMyComplains(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
          val  response = fRepository.getMyComplains(page)
            if (response is ResultStatus.Success) {
                fRepository.saveComplainsAsRequest(response.value.data.items)
            }
        }
    }

    fun saveComplaints(complaints: List<Complaints>) {
        viewModelScope.launch(Dispatchers.IO) {
            fRepository.saveComplaints(complaints)
        }
    }

    fun saveComplainsAsRequest(complains: List<Complaints>) {
        viewModelScope.launch(Dispatchers.IO) {
            fRepository.saveComplainsAsRequest(complains)
        }
    }

    fun deleteComplain(complaintId : String) : LiveData<ResultStatus<DeleteResponse>>{
        val response = MutableLiveData<ResultStatus<DeleteResponse>>()
        viewModelScope.launch {
            response.value = fRepository.deleteComplaint(complaintId)
        }
        return  response
    }

    fun deleteComplainFromDataBase(request: Request){
        viewModelScope.launch(Dispatchers.IO) {
            fRepository.deleteComplaintFromDataBase(request)
        }

    }
}