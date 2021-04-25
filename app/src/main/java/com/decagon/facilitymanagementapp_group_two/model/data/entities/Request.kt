package com.decagon.facilitymanagementapp_group_two.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.decagon.facilitymanagementapp_group_two.model.data.coverter.Converters
import com.decagon.facilitymanagementapp_group_two.model.data.coverter.RatingConverter

@Entity(tableName = "Requests")
data class Request(
    val categoryId: String? = null,
    @TypeConverters(Converters::class)
    val comments: List<Comment>? = null,
//<<<<<<< HEAD
//    var uuid : Long = 0,
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
//=======
//    @PrimaryKey(autoGenerate = true)
//    var uuid: Int? = null,
//    val id: String? = null,
//>>>>>>> develop
    val image: String? = null,
    val isTask: Boolean? = null,
    val question: String? = null,
    @TypeConverters(RatingConverter::class)
    val ratings: List<Rating>? = null,
    val title: String? = null,
    val type: String? = null,
    val userId: String? = null
)
