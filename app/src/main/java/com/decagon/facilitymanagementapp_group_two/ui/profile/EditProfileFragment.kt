package com.decagon.facilitymanagementapp_group_two.ui.profile

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentEditProfileBinding
import com.decagon.facilitymanagementapp_group_two.model.data.SsoResultBody
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileBody
import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileDetails
import com.decagon.facilitymanagementapp_group_two.model.data.UserProfile
import com.decagon.facilitymanagementapp_group_two.network.ApiCallStatus
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
    private lateinit var userDetails: SsoResultBody

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Gets SSO details from sharedPreference
         */
        val firstName = sharedPreferences.getString(FIRST_NAME, null)
        val lastName = sharedPreferences.getString(LAST_NAME, null)
        val email = sharedPreferences.getString(EMAIL, null)
        userDetails = SsoResultBody(firstName!!, lastName!!, email!!)


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
            findNavController().navigate(R.id.profileFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                ApiCallStatus.LOADING -> {
                    Snackbar.make(rootLayout, "Updating, please wait..", Snackbar.LENGTH_LONG)
                        .show()
                }
                ApiCallStatus.SUCCESS -> {
                    Snackbar.make(rootLayout, "Profile updated successfully", Snackbar.LENGTH_LONG)
                        .show()
                    findNavController().navigate(R.id.profileFragment)
                }
                ApiCallStatus.ERROR -> {
                    Snackbar.make(
                        rootLayout,
                        "Error occurred! Please try again",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

        // Update profile image codes
        rootLayout = binding.editFragmentProfileRootLayout
        profileImage = binding.editFragmentProfilePic

        binding.editFragmentCamera.setOnClickListener {
            AlertDialog.Builder(requireContext())
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

        binding.editFragmentProfileBtnSubmit.setOnClickListener {

            //      updateProfile()
            updateProfileImage()
            updateProfileDetails()
        }

        val userFullName = "${userDetails.firstName} ${userDetails.lastName}"
        binding.editFragmentProfileMail.text = userDetails.email
        binding.editFragmentProfileMainName.text = userFullName
        binding.editFragmentProfileName.text = userFullName
        val imgUrl = sharedPreferences.getString(PROFILE_IMG_URI, null)


        /**
         * Upload profile image from shared preference
         */
        imgUrl?.let {
            binding.editFragmentProfilePic.loadImage(imgUrl)
        }


    }

    private fun updateProfileDetails() {

        /**
         * Collect Input Data from the UI
         */
        val updateStack = binding.editFragmentProfileStackInput.text.toString().toUpperCase().trim()
        val updateSquad = binding.editFragmentProfileSquadInput.text.toString().trim()
        val updatePhoneNumber = binding.editFragmentProfilePhoneNumber.text.toString().trim()
        val username = binding.editFragmentProfileName.text.toString()
        val firstName = username.split(" ")[0]
        val lastName = username.split(" ")[1]
        val profileImage = "profileImage"
        val profileEmail = binding.editFragmentProfileMail.text.toString()
        val password = "12342"

        /**
         * Validate Input data, update input profile and show error respectively
         */

        val updateFormData = UpdateProfileBody(updateSquad, updateStack, updatePhoneNumber)

        if (updateFormData.inputValidation() == "Success") {
            val updateProfileDetails = UpdateProfileDetails(
                firstName,
                lastName,
                profileEmail,
                updateSquad,
                updateStack,
                updatePhoneNumber
            )
            viewModel.updateProfileDetails(updateProfileDetails)
        } else {
            Snackbar.make(rootLayout, updateFormData.inputValidation(), Snackbar.LENGTH_SHORT)
                .show()
        }


    }

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

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        requireActivity().startActivityFromFragment(this, intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            profileImage.setImageURI(data.data)
            imageUrl = data.data!!
        } else if (requestCode == 2 && resultCode == AppCompatActivity.RESULT_OK) {
            profileImage.setImageURI(imageUrl)
        }
    }

    private fun updateProfileImage() {
        if (imageUrl == null) {
            Snackbar.make(
                rootLayout, "Please select an image as a profile pic",
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        val parcelFileDescriptor = requireActivity().contentResolver
            .openFileDescriptor(imageUrl!!, "r", null) ?: return
        val file = File(
            requireActivity().cacheDir,
            requireActivity().contentResolver.getFileName(imageUrl!!)
        )
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        val body = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val image = MultipartBody.Part.createFormData("Image", file.name, body)

        viewModel.uploadProfileImage(image)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
