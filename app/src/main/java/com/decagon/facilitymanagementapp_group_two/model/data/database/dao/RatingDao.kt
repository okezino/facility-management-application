package com.decagon.facilitymanagementapp_group_two.model.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.decagon.facilitymanagementapp_group_two.model.data.RatingData
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Comments

/**
 * DAO for interacting with the Comments table in the database
 */
@Dao
interface RatingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rating: RatingData?)

    @Query("SELECT ratingId from RatingData WHERE complaintId = :complaintId")
    suspend fun getRatingIdByComplaintId(complaintId : String) : String



}
