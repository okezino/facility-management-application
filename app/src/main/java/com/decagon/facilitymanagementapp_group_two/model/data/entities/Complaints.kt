package com.decagon.facilitymanagementapp_group_two.model.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ComplaintItems(val data: ComplaintsData?)

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
    val userFirstName: String?,
    @SerializedName("lastName")
    val userLastName: String?,
    @SerializedName("avatarUrl")
    val userImgUrl: String?,
    @SerializedName("squad")
    val userSquad: String?,
    @SerializedName("time_created")
    val time: String?
)

@Entity(tableName = "apartment_complains")
data class ApartComplaints(
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
    val userFirstName: String?,
    @SerializedName("lastName")
    val userLastName: String?,
    @SerializedName("avatarUrl")
    val userImgUrl: String?,
    @SerializedName("squad")
    val userSquad: String?,
    @SerializedName("time_created")
    val time: String?
)

@Entity(tableName = "appliance_complains")
data class ApplianceComplaints(
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
    val userFirstName: String?,
    @SerializedName("lastName")
    val userLastName: String?,
    @SerializedName("avatarUrl")
    val userImgUrl: String?,
    @SerializedName("squad")
    val userSquad: String?,
    @SerializedName("time_created")
    val time: String?
)

@Entity(tableName = "others_complains")
data class OthersComplaints(
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
    val userFirstName: String?,
    @SerializedName("lastName")
    val userLastName: String?,
    @SerializedName("avatarUrl")
    val userImgUrl: String?,
    @SerializedName("squad")
    val userSquad: String?,
    @SerializedName("time_created")
    val time: String?
)

