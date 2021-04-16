package com.decagon.facilitymanagementapp_group_two.model.data.entities

import androidx.room.Entity


@Entity(tableName = "AuthResponse")
data class AuthResponse(val accessToken : String,val id : String)