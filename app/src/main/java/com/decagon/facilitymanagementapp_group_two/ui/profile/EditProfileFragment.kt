package com.decagon.facilitymanagementapp_group_two.ui.profile

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentEditProfileBinding
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileBody
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileDetails
import com.decagon.facilitymanagementapp_group_two.model.data.entities.UserData
import com.decagon.facilitymanagementapp_group_two.network.ApiResponseHandler
import com.decagon.facilitymanagementapp_group_two.utils.*
import com.decagon.facilitymanagementapp_group_two.viewmodel.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private lateinit var profileImage: ImageView
    private var imageUrl: Uri? = null
    private lateinit var rootLayout: ConstraintLayout
    private val viewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentEditProfileBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Update Status Bar Colour
         */
        setStatusBarBaseColor(requireActivity(), requireContext(), R.color.smokeWhite)

        /**
         * Inflate the view binding with Edit Fragment Layout
         */
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        /**
         * Navigate back to Profile Fragment
         */
        binding.editFragmentProfileBackBtn.setOnClickListener {
            findNavController().popBackStack()
           // findNavController().navigate(R.id.profileFragment)
        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rootLayout = binding.editFragmentProfileRootLayout
        profileImage = binding.editFragmentProfilePic

        /**
         * Displays an alert dialog with options for users to
         * select image for their profile
         */
        binding.editFragmentCamera.setOnClickListener {
            AlertDialog.Builder(requireContext(), R.style.MyDialogTheme)
                .setTitle("Change Photo")
                .setItems(
                    arrayOf("Take Photo", "Choose Photo")
                ) { _, which ->
                    if (which == 0) {
                        takePhoto()
                    } else if (which == 1) {
                        openGallery()
                    }
                }
                .setNegativeButton("CANCEL") { _, _ -> }
                .create()
                .show()
        }

        binding.editFragmentProfileBtnSubmit.setOnClickListener { updateProfileDetails() }

        /**
         * Gets Profile details from DataBase and update the View
         */

        viewModel.userData.observe(
            viewLifecycleOwner,
            Observer { user ->
                binding.editFragmentProfileMainName.text = "${user.firstName}  ${user.lastName}"
                binding.editFragmentProfileStackSquadText.text = "${user.stack} - ${user.squad}"
                binding.editFragmentProfileMail.text = user.email
                binding.editFragmentProfileName.text = "${user.firstName} ${user.lastName}"
                binding.editFragmentProfilePhoneNumber.setText(user.phoneNumber)
                binding.editFragmentProfileStackInput.setText(user.stack)
                binding.editFragmentProfileSquadInput.setText(user.squad)
               // binding.editFragmentProfileName.error = "error"
            }
        )

        /**
         * Upload profile image from shared preference
         */
        val imgUrl = sharedPreferences.getString(PROFILE_IMG_URI, null)
        imgUrl?.let {
            binding.editFragmentProfilePic.loadImage(imgUrl)
        }
    }

    private fun updateProfileDetails() {

        /**
         * Collect Input Data from the UI
         */
        val updateStack = binding.editFragmentProfileStackInput.text.toString().toUpperCase().trim()
        val updateSquad = binding.editFragmentProfileSquadInput.text.toString().toUpperCase().trim()
        val updatePhoneNumber = binding.editFragmentProfilePhoneNumber.text.toString().trim()
        val username = binding.editFragmentProfileName.text.toString()
        val (firstName, lastName) = username.split(" ")
        val profileEmail = binding.editFragmentProfileMail.text.toString()

        /**
         * Validate Input data, update input profile and show error respectively
         */

        val updateFormData = UpdateProfileBody(updateSquad, updateStack, updatePhoneNumber)
        if (updateFormData.inputValidation() == "Success") {
            val updateProfileDetails = UpdateProfileDetails(
                firstName =  firstName,
                lastName =   lastName,
                userName = profileEmail,
                stack = updateStack,
                squad = updateSquad,
                phoneNumber = updatePhoneNumber,
                gender = "m"
            )
            val result = viewModel.updateProfileDetails(updateProfileDetails)
            val user = UserData(firstName, lastName, "null", profileEmail, updateStack, updatePhoneNumber, updateSquad)

            ApiResponseHandler(result, this, view) {
                when (it.value.code()) {
                    204 -> {
                        /**
                         * Updated user profile to Database
                         */
                        viewModel.updateUserToDataBase(user)

                        view?.showSnackBar("Profile details updated successfully")
                        findNavController().popBackStack()
                        findNavController().navigate(R.id.profileFragment)
                    }
                    401 -> {
                        view?.showSnackBar("Unauthorized: Your session has expired. Please logout and login again to continue")
                    }
                    else -> view?.showSnackBar("Error: Unable to established connection with server")
                }
                Log.d("ApiCall", "${it.value}")
            }
        } else {
            Snackbar.make(rootLayout, updateFormData.inputValidation(), Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    /**
     * Start an intent for capturing photos using the device's camera
     */
    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file = File(requireActivity().externalCacheDir!!.absoluteFile, "MyPhoto.jpg")
        imageUrl = FileProvider.getUriForFile(
            requireContext(),
            requireActivity().applicationContext.packageName + ".provider", file
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl)
        try {
            requireActivity().startActivityFromFragment(this, intent, 2)
        } catch (e: ActivityNotFoundException) {
            Snackbar.make(rootLayout, "Camera not found", Snackbar.LENGTH_LONG).show()
        }
    }

    /**
     * Start an intent for selecting images/photos from the device's gallery
     */
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        requireActivity().startActivityFromFragment(this, intent, 1)
    }

    /**
     * Handles the result of the intents and update the user's profile
     * image accordingly
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            imageUrl = data.data!!
            updateProfileImage()
        } else if (requestCode == 2 && resultCode == AppCompatActivity.RESULT_OK) {
            updateProfileImage()
        }
    }

    /**
     * Handle the logic of reading/writing files from the device's shared storage
     * and upload the image to the server using the helper method from the ProfileViewModel
     */
    private fun updateProfileImage() {
        val parcelFileDescriptor = requireActivity().contentResolver
            .openFileDescriptor(imageUrl!!, "r", null) ?: return
        val file = File(
            requireActivity().cacheDir,
            requireActivity().contentResolver.getFileName(imageUrl!!)
        )

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        val body = file!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val image = MultipartBody.Part.createFormData("Image", file!!.name, body)

        val serverResponse = viewModel.uploadProfileImage(image)

        ApiResponseHandler(serverResponse, this, view) {
            viewModel.saveData(PROFILE_IMG_URI, it.value.data.url)
            view?.showSnackBar("Profile image updated successfully")
            imageUrl?.let { profileImage.loadImage(it.toString()) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
