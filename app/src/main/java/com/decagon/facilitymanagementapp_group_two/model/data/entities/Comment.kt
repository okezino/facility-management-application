package com.decagon.facilitymanagementapp_group_two.model.data.entities

import com.google.gson.annotations.SerializedName

data class Comment(
    val comment: String,
    val replies: List<Any>,
    val user: UserX,
    @SerializedName("time_created")
    val commentTime: String
)
