package com.decagon.facilitymanagementapp_group_two.model.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Comment
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request

/**
 * DAO for interacting with the Request table in the database
 */
@Dao
interface RequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(request: Request)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(requests: List<Request>)

    @Query("SELECT * FROM Requests ORDER BY uuid DESC")
    fun getAllRequest() : LiveData<List<Request>?>

    // get a particular request by id
    @Query("SELECT * from Requests WHERE id = :id ")
    fun getCommentById(id : String) : LiveData<Request>
}
