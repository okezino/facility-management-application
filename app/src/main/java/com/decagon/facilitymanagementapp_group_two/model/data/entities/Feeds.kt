package com.decagon.facilitymanagementapp_group_two.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "All feeds")
data class Feeds(
    val description: String,
  //  val feedId : Int? = null,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String
)