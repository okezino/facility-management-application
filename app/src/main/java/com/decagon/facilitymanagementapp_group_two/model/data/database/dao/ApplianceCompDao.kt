package com.decagon.facilitymanagementapp_group_two.model.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decagon.facilitymanagementapp_group_two.model.data.entities.ApplianceComplaints
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints

@Dao
interface ApplianceCompDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveComplaints(complaints: List<ApplianceComplaints>?)

    @Query("SELECT * FROM appliance_complains WHERE category LIKE :cat")
    fun getComplaintsByCat(cat: String): PagingSource<Int, ApplianceComplaints>


    @Query("DELETE FROM appliance_complains")
    suspend fun clearComplains()


}