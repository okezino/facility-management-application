package com.decagon.facilitymanagementapp_group_two.ui.profile

import android.content.ActivityNotFoundException
import android.content.Intent
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
import androidx.navigation.fragment.findNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.databinding.FragmentEditProfileBinding
import com.decagon.facilitymanagementapp_group_two.model.data.UserProfile
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

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private lateinit var profileImage: ImageView
    private var imageUrl: Uri? = null
    private lateinit var rootLayout: ConstraintLayout
    private val viewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentEditProfileBinding? = null
    private val binding
        get() = _binding!!


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
            findNavController().navigate(R.id.profileFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Update profile image codes
        rootLayout = binding.editFragmentProfileRootLayout
        profileImage = binding.editFragmentProfilePic

    /**
     * Displays an alert dialog with options for users to
     * select image for their profile
     */
        binding.editFragmentCamera.setOnClickListener {
            AlertDialog.Builder(requireContext())
                    .setTitle("Change Photo")
                    .setItems(arrayOf("Take Photo", "Choose Photo")
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
            updateProfile()
        }

    }

    private fun updateProfile() {

        /**
         * Collect Input Data from the UI
         */
        val updateStack = binding.editFragmentProfileStackInput.text.toString().toUpperCase().trim()
        val updateSquad = binding.editFragmentProfileSquadInput.text.toString().trim()
        val updatePhoneNumber = binding.editFragmentProfilePhoneNumber.text.toString().trim()
        val username = binding.editFragmentProfileName.text.toString()
        val profileImage = "profileImage"
        val profileEmail = binding.editFragmentProfileMail.text.toString()
        val password = "12342"

        /**
         * Validate Input data and Toast the values
         */

        if (squadInputValidation(updateSquad) && stackValidation(updateStack) && phoneNumberValidator(updatePhoneNumber)) {
            var user = UserProfile(username, profileImage, profileEmail, updatePhoneNumber, updateSquad, updateStack, password)

            Toast.makeText(requireContext(), user.toString(), Toast.LENGTH_SHORT).show()
        } else {
            if (!squadInputValidation(updateSquad)) Toast.makeText(requireContext(), "Invalid Squad", Toast.LENGTH_SHORT).show()
            else if (!stackValidation(updateStack)) Toast.makeText(requireContext(), "Invalid Stack", Toast.LENGTH_SHORT).show()
            else {
                Toast.makeText(requireContext(), "Invalid Phone Number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Start an intent for capturing photos using the device's camera
     */
    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file = File(requireActivity().externalCacheDir!!.absoluteFile, "MyPhoto.jpg")
        imageUrl = FileProvider.getUriForFile(requireContext(),
                requireActivity().applicationContext.packageName + ".provider", file)
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
        val file = File(requireActivity().cacheDir,
                requireActivity().contentResolver.getFileName(imageUrl!!))
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        val body = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val image = MultipartBody.Part.createFormData("Image", file.name, body)

        viewModel.uploadProfileImage(image, view, profileImage, imageUrl)
    }
}
