package com.decagon.facilitymanagementapp_group_two.model.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decagon.facilitymanagementapp_group_two.model.data.entities.ApartComplaints
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints

@Dao
interface ApartComplainsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveApartComplaints(complaints: List<ApartComplaints>?)

    @Query("SELECT * FROM apartment_complains WHERE category LIKE :cat")
    fun getComplaintsByCat(cat: String): PagingSource<Int, ApartComplaints>


    @Query("DELETE FROM apartment_complains")
    suspend fun clearApartComplains()
}
