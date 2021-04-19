package com.decagon.facilitymanagementapp_group_two.model.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Feeds

@Dao
interface FeedsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFeeds(feedsItems: List<Feeds>)

    @Query("SELECT * FROM Feeds" )
    fun getFeeds(): LiveData<List<Feeds>>
}