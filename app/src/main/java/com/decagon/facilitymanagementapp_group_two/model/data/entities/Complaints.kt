package com.decagon.facilitymanagementapp_group_two.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.decagon.facilitymanagementapp_group_two.utils.USER_ID
import com.google.gson.annotations.SerializedName

data class ComplaintItems(val data: ComplaintsData)

data class ComplaintsData(
    val totalNumberOfPages: Long,
    val currentPage: Long,
    val items: List<Complaints>
)

@Entity(tableName = "Complaints/Requests")
data class Complaints(
    @SerializedName("title")
    val subject: String?,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @SerializedName("question")
    val description: String,
    @SerializedName("type")
    val category: String?,
    @SerializedName("image")
    val complaintImgUrl: String?,
    @SerializedName("firstName")
    val userFirstName: String,
    @SerializedName("lastName")
    val userLastName: String,
    @SerializedName("avatarUrl")
    val userImgUrl: String?,
    @SerializedName("squad")
    val userSquad: String?
)