package com.decagon.facilitymanagementapp_group_two.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

data class FeedItems(val data: FeedsData)

data class FeedsData(val items: List<Feeds>)

@Entity(tableName = "Feeds")
data class Feeds(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val description: String
)