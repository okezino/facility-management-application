package com.decagon.facilitymanagementapp_group_two.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User(
    val userName: String,
    val profileImageUrl: String,
    val email: String,
    val phoneNumber: String,
    val role: String,
    val stack: String,
    val password: String
) {
    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0
}
