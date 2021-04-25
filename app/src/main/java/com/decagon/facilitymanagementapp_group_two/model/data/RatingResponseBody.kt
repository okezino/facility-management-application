package com.decagon.facilitymanagementapp_group_two.model.data

data class RatingResponseBody(
    val `data`: RatingData? = null,
    val message: String,
    val success: Boolean
)