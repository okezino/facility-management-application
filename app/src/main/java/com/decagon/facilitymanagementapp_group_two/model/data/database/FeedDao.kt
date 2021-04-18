package com.decagon.facilitymanagementapp_group_two.model.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Feeds
import com.decagon.facilitymanagementapp_group_two.model.data.entities.User
import com.decagon.facilitymanagementapp_group_two.model.data.entities.UserData

/**
 * DAO for interacting with the Request table in the database
 */
@Dao
interface FeedDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(feedItems: List<Feeds>)

//    @Update
//    fun update(user: User)

    @Query("SELECT id FROM `All feeds` WHERE name = :name")
    suspend fun getFeedId(name : String): String

//    @Query("SELECT * FROM Users ORDER BY userId DESC LIMIT 1")
//    fun getLatestUser(): User
//
//    @Query("DELETE FROM Users")
//    fun clear()
//
//    @Query("Select * from Users Order by userId desc")
//    fun getAllUsers(): LiveData<List<User>>
}
