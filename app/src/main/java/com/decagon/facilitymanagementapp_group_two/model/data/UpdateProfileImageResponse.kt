package com.decagon.facilitymanagementapp_group_two.model.data

data class UpdateProfileImageResponse(val success: Boolean, val data: ImageData, val message: String)

data class ImageData(val publicId: String, val url: String)
