package com.decagon.facilitymanagementapp_group_two.model.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Feeds

/**
 * DAO for interacting with the Feeds table in the database
 */
@Dao
interface FeedDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllFeeds(feedItems: List<Feeds>)

    @Query("SELECT id FROM `All feeds` WHERE name = :name")
    suspend fun getFeedId(name : String): String

    @Query("SELECT id FROM `All feeds` WHERE name = :name")
    fun getFeedIdByName(name : String): LiveData<String>

    @Query("SELECT * FROM `All feeds`")
    suspend fun getAllFeedsId(): List<Feeds>
}
