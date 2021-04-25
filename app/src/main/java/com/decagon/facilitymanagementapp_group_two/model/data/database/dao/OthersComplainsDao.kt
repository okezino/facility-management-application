package com.decagon.facilitymanagementapp_group_two.model.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import com.decagon.facilitymanagementapp_group_two.model.data.entities.OthersComplaints

@Dao
interface OthersComplainsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveComplaints(complaints: List<OthersComplaints>?)

    @Query("SELECT * FROM others_complains WHERE category LIKE :cat")
    fun getComplaintsByCat(cat: String): PagingSource<Int, OthersComplaints>


    @Query("DELETE FROM others_complains")
    suspend fun clearComplains()

}