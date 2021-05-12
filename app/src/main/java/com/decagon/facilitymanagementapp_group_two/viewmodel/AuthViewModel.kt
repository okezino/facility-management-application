package com.decagon.facilitymanagementapp_group_two.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.facilitymanagementapp_group_two.model.data.FeedResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.ResponseBody
import com.decagon.facilitymanagementapp_group_two.model.data.entities.AuthResponse
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Feeds
import com.decagon.facilitymanagementapp_group_two.model.data.entities.User
import com.decagon.facilitymanagementapp_group_two.model.data.entities.UserData
import com.decagon.facilitymanagementapp_group_two.model.repository.auth.AuthRepository
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
class AuthViewModel @Inject constructor (private val authRepository: AuthRepository) : ViewModel() {

    /**
     * Sends SSO details to endpoint and perform appropriate actions
     * when the response from the network call is successful else
     * notify user's of the error
     */
    fun getToken(token: String): LiveData<ResultStatus<ResponseBody>> {
        val response = MutableLiveData<ResultStatus<ResponseBody>>()
        viewModelScope.launch {
            response.value = authRepository.postAuthDetails(token)
        }
        return response
    }


    fun getUserData(userId: String): LiveData<ResultStatus<User>> {
        val response = MutableLiveData<ResultStatus<User>>()
        viewModelScope.launch {
            response.value = authRepository.getUsers(userId)
        }
        return response
    }

    fun saveUserToDatabase(user: UserData) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.saveUser(user)
        }
    }

    fun getAllFeeds(): LiveData<ResultStatus<FeedResponseBody>> {
        val response = MutableLiveData<ResultStatus<FeedResponseBody>>()
        viewModelScope.launch {
            response.value = authRepository.getAllFeeds()
        }
        return response
    }

    fun saveFeedToDb(feeds: List<Feeds>) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.saveFeedsToDb(feeds)
        }
    }

    fun saveFeedsIdToPref(feeds: List<Feeds>) {
        viewModelScope.launch(Dispatchers.IO) {
            for (feed in feeds) {
                when (feed.name) {
                    APARTMENT -> authRepository.saveDataInPref(APARTMENT, feed.id)
                    APPLIANCE -> authRepository.saveDataInPref(APPLIANCE, feed.id)
                    FOOD -> authRepository.saveDataInPref(FOOD, feed.id)
                    OTHERS -> authRepository.saveDataInPref(OTHERS, feed.id)
                }
            }
        }
    }
}
