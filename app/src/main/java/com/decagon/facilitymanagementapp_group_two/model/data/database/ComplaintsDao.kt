package com.decagon.facilitymanagementapp_group_two.model.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints

@Dao
interface ComplaintsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveComplaints(complaints: List<Complaints>)

    @Query("SELECT * FROM `Complaints/Requests` WHERE category = :cat")
    fun getComplaintsByCat(cat: String): LiveData<List<Complaints>?>

}