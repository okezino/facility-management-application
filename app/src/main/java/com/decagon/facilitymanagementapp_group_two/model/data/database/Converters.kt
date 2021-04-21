package com.decagon.facilitymanagementapp_group_two.model.data.database

import androidx.room.TypeConverter
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Comment
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Rating
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



class Converters {
    private val gson = Gson()
    @TypeConverter
    fun stringToCommentList(data: String?): List<Comment>? {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<Comment>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun commentListToString(comments: List<Comment>?): String {
        return gson.toJson(comments)
    }
}

