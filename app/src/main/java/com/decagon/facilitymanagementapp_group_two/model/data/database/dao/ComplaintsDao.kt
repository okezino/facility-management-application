package com.decagon.facilitymanagementapp_group_two.model.data.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import kotlinx.coroutines.flow.Flow

@Dao
interface ComplaintsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveComplaints(complaints: List<Complaints>?)

    @Query("SELECT * FROM `Complaints/Requests` WHERE category LIKE :cat")
    fun getComplaintsByCat(cat: String): PagingSource<Int, Complaints>

//    @Query("SELECT * FROM `Complaints/Requests` WHERE category LIKE :cat")
//    fun getComplaintsByCat(cat: String): LiveData<List<Complaints>?>

    @Query("DELETE FROM `Complaints/Requests`")
    suspend fun clearComplains()

}