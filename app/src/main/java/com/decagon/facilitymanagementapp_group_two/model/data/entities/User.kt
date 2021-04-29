package com.decagon.facilitymanagementapp_group_two.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class User(
    val data: UserData
)

@Entity(tableName = "Users")
data class UserData(
    val firstName: String?,
    val lastName: String?,
    @SerializedName("avatarUrl")
    val profileImageUrl: String?,
    @SerializedName("userName")
    val email: String,
    val stack: String?,
    val phoneNumber: String?,
    val squad: String?,
    var isProfileCompleted: Boolean? = null,
    @PrimaryKey(autoGenerate = false)
    var userId: String = ""
)
