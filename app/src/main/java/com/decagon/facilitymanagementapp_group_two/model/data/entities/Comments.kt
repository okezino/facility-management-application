package com.decagon.facilitymanagementapp_group_two.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Comments")
data class Comments(
    val requestId: Long,
    val userName: String,
    val body: String,
    val userProfileImageUrl: String,
    val time: String
) {
    @PrimaryKey(autoGenerate = true)
    var commentId: Long = 0
}
