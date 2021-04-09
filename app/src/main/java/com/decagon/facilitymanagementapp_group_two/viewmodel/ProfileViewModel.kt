package com.decagon.facilitymanagementapp_group_two.viewmodel

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileImageResponse
import com.decagon.facilitymanagementapp_group_two.model.repository.auth.AuthRepository
import com.decagon.facilitymanagementapp_group_two.network.ApiResponseHandler
import com.decagon.facilitymanagementapp_group_two.network.ResultStatus
import com.decagon.facilitymanagementapp_group_two.utils.PROFILE_IMG_URI
import com.decagon.facilitymanagementapp_group_two.utils.loadImage
import com.decagon.facilitymanagementapp_group_two.utils.showSnackBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    /**
     * Method to upload profile image to the server and keeps the user aware
     * of the various states of the transaction or process.
     */
    fun uploadProfileImage(image: MultipartBody.Part, view: View?, imageView: ImageView, imageUrl: Uri?) {
        view?.showSnackBar("Loading... Please wait")
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
}

