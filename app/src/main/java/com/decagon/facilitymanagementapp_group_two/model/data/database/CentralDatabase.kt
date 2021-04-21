package com.decagon.facilitymanagementapp_group_two.model.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.decagon.facilitymanagementapp_group_two.model.data.entities.*

/**
 * This is our MainDatabase class. The database consists of three tables/entities
 * which are used for storing Comments, Requests and Users separately.
 */

@Database(
    entities = [
        Comments::class,
        Request::class,
        UserData::class,
        AuthResponse::class,
        Feeds::class,
        Complaints::class
               ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class, RatingConverter::class)
abstract class CentralDatabase : RoomDatabase() {

    abstract val commentDao: CommentsDao
    abstract val requestDao: RequestDao
    abstract val userDao: UserDao
    abstract val authResponseDao: AuthResponseDao
    abstract val complaintsDao: ComplaintsDao
    abstract val feedDao : FeedDao

}
