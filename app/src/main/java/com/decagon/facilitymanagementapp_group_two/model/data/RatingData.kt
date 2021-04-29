package com.decagon.facilitymanagementapp_group_two.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RatingData(
    @PrimaryKey
    val complaintId: String,
    val rating: Int?,
    val ratingId: String?,
    val userId: String?,
)