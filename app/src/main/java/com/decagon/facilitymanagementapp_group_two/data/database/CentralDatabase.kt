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

    companion object {
        /**
         *  Singleton pattern to prevents multiple instances of database opening at the
         *  same time.
         */
        @Volatile
        private var INSTANCE: CentralDatabase? = null

        fun getInstance(context: Context): CentralDatabase {

            // if the INSTANCE is not null, then return it,
            // if it is, then create the database

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        CentralDatabase::class.java,
                        "comments_requests_user_record")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}