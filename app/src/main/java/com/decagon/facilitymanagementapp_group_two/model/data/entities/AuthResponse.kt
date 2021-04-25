package com.decagon.facilitymanagementapp_group_two.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AuthResponse")
data class AuthResponse(
    val accessToken: String,
    @PrimaryKey
    val id: String
)
