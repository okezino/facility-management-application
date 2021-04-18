package com.decagon.facilitymanagementapp_group_two.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "All feeds")
data class Feeds(
    val description: String,
    @PrimaryKey(autoGenerate = true)
    val feedId : Int? = null,
    val id: String,
    val name: String
)