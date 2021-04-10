package com.decagon.facilitymanagementapp_group_two.viewmodel

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileDetails
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileImageResponse
import com.decagon.facilitymanagementapp_group_two.model.repository.auth.AuthRepository
import com.decagon.facilitymanagementapp_group_two.network.ApiResponseHandler
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
import com.decagon.facilitymanagementapp_group_two.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    /**
     * Method to upload profile image to the server and keeps the user aware
     * of the various states of the transaction or process.
     */
    fun uploadProfileImage(image: MultipartBody.Part, view: View?, imageView: ImageView, imageUrl: Uri?) {
        view?.showSnackBar("Updating... Please wait")
        viewModelScope.launch {
            val serverResponse = authRepository.updateProfileImage(image)
            val action: (result: ResultStatus<UpdateProfileImageResponse>) -> Unit = {
                if (it is ResultStatus.Success) {
                    authRepository.saveDataInPref(PROFILE_IMG_URI, it.value.data.url)
                    view?.showSnackBar("Profile image updated successfully")
                    imageUrl?.let { imageView.loadImage(it.toString()) }
                }
            }
            ApiResponseHandler(serverResponse, action, view)
        }
    }

    /**
     * Method to update and upload profile details to the server and keeps the user aware
     * of the various states of the transaction or process.
     */
    fun updateProfileDetails(
        updateProfileDetails: UpdateProfileDetails,
        view: View?,
        navController: NavController
    ) {
        view?.showSnackBar("Updating... Please wait")
        viewModelScope.launch {
            val response = authRepository.updateProfileDetails(updateProfileDetails)
            val action: (result: ResultStatus<Response<Unit>>) -> Unit = {
                if (it is ResultStatus.Success) {
                    authRepository.apply {
                        saveDataInPref(FIRST_NAME, updateProfileDetails.firstName)
                        saveDataInPref(LAST_NAME, updateProfileDetails.lastName)
                        saveDataInPref(USER_NAME, updateProfileDetails.userName)
                        saveDataInPref(SQUAD, updateProfileDetails.squad)
                        saveDataInPref(PHONE_NUMBER, updateProfileDetails.phoneNumber)
                        saveDataInPref(STACK, updateProfileDetails.gender)
                    }
                    view?.showSnackBar("Profile updated successfully")
                    navController.navigate(R.id.profileFragment)
                }
            }
            ApiResponseHandler(response, action, view)
        }
    }
}
