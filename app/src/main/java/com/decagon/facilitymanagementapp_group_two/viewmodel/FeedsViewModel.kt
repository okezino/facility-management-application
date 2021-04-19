package com.decagon.facilitymanagementapp_group_two.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.facilitymanagementapp_group_two.model.data.entities.ComplaintItems
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import com.decagon.facilitymanagementapp_group_two.model.data.entities.FeedItems
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Feeds
import com.decagon.facilitymanagementapp_group_two.model.repository.feeds.FeedsRepository
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
class FeedsViewModel @Inject constructor(private val feedsRepository: FeedsRepository) : ViewModel() {

    val feedCategory = feedsRepository.getFeedsFromDb()

    val foodComplaints = feedsRepository.getComplaintsFromDb(FOOD)

    val apartmentComplaints = feedsRepository.getComplaintsFromDb(APARTMENT)

    val appliancesComplaints = feedsRepository.getComplaintsFromDb(APPLIANCE)

    val otherComplaints = feedsRepository.getComplaintsFromDb(OTHERS)

    val myComplaints = feedsRepository.getMyComplaintsFromDb()

    init {
        getAndSaveFeeds()
    }

//    fun getFeedsCategories(): LiveData<ResultStatus<FeedItems>> {
//        val result = MutableLiveData<ResultStatus<FeedItems>>()
//        viewModelScope.launch {
//            result.value = feedsRepository.getFeedCategories()
//        }
//        return result
//    }
//
//    fun saveFeedsCategories(items: List<Feeds>) {
//        viewModelScope.launch(Dispatchers.IO) {
//            feedsRepository.saveFeeds(items)
//        }
//    }

    private fun getAndSaveFeeds() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val feeds = feedsRepository.getFeedCategories()) {
                is ResultStatus.Success -> {
                    val (food) = feeds.value.data.items.filter { it.name == FOOD }
                    val (apartment) = feeds.value.data.items.filter { it.name == APARTMENT  }
                    val (appliance) = feeds.value.data.items.filter { it.name == APPLIANCE }
                    val (other) = feeds.value.data.items.filter { it.name == OTHERS }
                    feedsRepository.apply {
                        saveFeedId(FOOD, food.id)
                        saveFeedId(APARTMENT, apartment.id)
                        saveFeedId(APPLIANCE, appliance.id)
                        saveFeedId(OTHERS, other.id)
                    }
                }
                is ResultStatus.GenericError -> {
                    Log.d("FeedsCateResult", feeds.error.toString())
                }
                else -> {
                    Log.d("FeedsCateResult", "Network Error")
                }
            }
        }
    }

    fun getComplaints(feedId: String, page: Int): LiveData<ResultStatus<ComplaintItems>> {
        val response = MutableLiveData<ResultStatus<ComplaintItems>>()
        viewModelScope.launch {
            response.value = feedsRepository.getComplaints(feedId, page)
        }
        return response
    }

    fun saveComplaints(complaints: List<Complaints>) {
        viewModelScope.launch(Dispatchers.IO) {
            feedsRepository.saveComplaints(complaints)
        }
    }

    fun init() {
        Log.d("FeedsViewModel", "init() called")
    }
}