package com.decagon.facilitymanagementapp_group_two.model.data.database

import androidx.room.*
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request

/**
 * DAO for interacting with the Request table in the database
 */
@Dao
interface RequestDao {

    @Insert
    suspend fun insert(request: Request)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(requests: List<Request>)

    @Update
    fun update(request: Request)
}
