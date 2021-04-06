package com.decagon.facilitymanagementapp_group_two.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Requests")
data class Request(
    val userId: Long,
    val feedCategory: String,
    val userName: String,
    val subject: String,
    val body: String,
    val date: String,
    var requestImageUri: String? = null,
) {
    @PrimaryKey(autoGenerate = true)
    var requestId: Long = 0
    var numberOfComment: Long = 0
    var numberOfLikes: Long = 0
}
