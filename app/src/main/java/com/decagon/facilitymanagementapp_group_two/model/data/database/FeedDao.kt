package com.decagon.facilitymanagementapp_group_two.model.data.database

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

}
