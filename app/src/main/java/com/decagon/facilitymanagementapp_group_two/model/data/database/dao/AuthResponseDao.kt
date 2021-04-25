package com.decagon.facilitymanagementapp_group_two.model.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decagon.facilitymanagementapp_group_two.model.data.entities.AuthResponse

@Dao
interface AuthResponseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(authResponse: AuthResponse)

    @Query("SELECT * FROM AuthResponse")
    fun getAuthResponse(): AuthResponse
}
