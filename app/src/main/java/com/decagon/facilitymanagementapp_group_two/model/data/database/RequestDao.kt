package com.decagon.facilitymanagementapp_group_two.model.data.database

import androidx.lifecycle.LiveData
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
    fun insertAll(requests: List<Request>)

    @Update
    fun update(request: Request)

//    @Query("SELECT * from Requests WHERE feedCategory = :feedCategory")
//    fun get(feedCategory: String): LiveData<List<Request>?>

//    @Query("SELECT * FROM Requests ORDER BY requestId DESC LIMIT 1")
//    fun getLatestRequest(): Request
//
//    @Query("DELETE FROM Requests")
//    fun clear()
//
//    @Query("Select * from Requests Order by requestId desc")
//    fun getAllRequests(): LiveData<List<Request>>
}
