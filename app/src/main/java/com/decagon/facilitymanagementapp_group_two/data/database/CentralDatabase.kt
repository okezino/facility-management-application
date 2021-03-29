package com.decagon.facilitymanagementapp_group_two.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.decagon.facilitymanagementapp_group_two.data.entities.Comments
import com.decagon.facilitymanagementapp_group_two.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.data.entities.User

/**
 * This is our MainDatabase class. The database consists of three tables/entities
 * which are used for storing Comments, Requests and Users separately.
 */

@Database(entities = [Comments::class, Request::class, User::class], version = 1, exportSchema = false)
abstract class CentralDatabase : RoomDatabase() {

    abstract val commentDao: CommentsDao
    abstract val requestDao: RequestDao
    abstract val userDao: UserDao

}