package com.decagon.facilitymanagementapp_group_two.model.data.entities

data class Comment(
    val comment: String,
    val replies: List<Any>,
    val user: UserX
)
