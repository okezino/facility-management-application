package com.decagon.facilitymanagementapp_group_two.data

data class UserProfile(
    val userName: String,
    val profileImageUrl: String,
    val email: String,
    val phoneNumber: String,
    val squad: String,
    val stack: String,
    val password: String
)
