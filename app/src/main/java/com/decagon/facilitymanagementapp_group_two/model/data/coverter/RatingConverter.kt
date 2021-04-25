package com.decagon.facilitymanagementapp_group_two.model.data.coverter

import androidx.room.TypeConverter
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Comment
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Rating
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



class RatingConverter{
    private val gson = Gson()
    @TypeConverter
    fun stringToRatingList(data: String?): List<Rating>? {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<Rating>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun ratingListToString(ratings: List<Rating>?): String {
        return gson.toJson(ratings)
    }
}

