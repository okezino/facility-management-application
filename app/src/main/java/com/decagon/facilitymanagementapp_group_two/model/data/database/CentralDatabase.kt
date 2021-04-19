package com.decagon.facilitymanagementapp_group_two.model.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.decagon.facilitymanagementapp_group_two.model.data.entities.*

/**
 * This is our MainDatabase class. The database consists of three tables/entities
 * which are used for storing Comments, Requests and Users separately.
 */

@Database(entities = [Comments::class, Request::class, UserData::class, AuthResponse::class, Feeds::class], version = 1, exportSchema = false)
abstract class CentralDatabase : RoomDatabase() {

    abstract val commentDao: CommentsDao
    abstract val requestDao: RequestDao
    abstract val userDao: UserDao
    abstract val authResponseDao: AuthResponseDao
    abstract val feedDao : FeedDao
}
