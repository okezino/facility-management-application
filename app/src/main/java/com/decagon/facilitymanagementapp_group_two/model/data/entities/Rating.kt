package com.decagon.facilitymanagementapp_group_two.model.data.entities

data class Rating(
    val rating: Int,
    val userId: String,
    val complaintId: String,
    val timeCreated : String
)

