package com.decagon.facilitymanagementapp_group_two.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.decagon.facilitymanagementapp_group_two.model.data.database.Converters
import com.decagon.facilitymanagementapp_group_two.model.data.database.RatingConverter

@Entity(tableName = "Requests")
data class Request(
    val categoryId: String? = null,
    @TypeConverters(Converters::class)
    val comments: List<Comment>? = null,
    @PrimaryKey(autoGenerate = true)
    var uuid : Int? = null,
    val id: String? = null,
    val image: String? = null,
    val isTask: Boolean? = null,
    val question: String? = null,
    @TypeConverters(RatingConverter::class)
    val ratings: List<Rating>? = null,
    val title: String? = null,
    val type: String? = null,
    val userId: String? = null
)




