package com.decagon.facilitymanagementapp_group_two.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Requests")
data class Request(
    val categoryId: String? = null,
    val comments: String? = null,
    @PrimaryKey(autoGenerate = true)
    var uuid : Int? = null,
    val id: String? = null,
    val image: String? = null,
    val isTask: Boolean? = null,
    val question: String? = null,
    val ratings: Int? = null,
    val title: String? = null,
    val type: String? = null,
    val userId: String? = null
)


