package com.decagon.facilitymanagementapp_group_two.model.data.database.remotekeys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decagon.facilitymanagementapp_group_two.model.data.entities.ApartmentRemoteKeys
import com.decagon.facilitymanagementapp_group_two.model.data.entities.ComplainRemoteKeys

@Dao
interface ApartRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<ApartmentRemoteKeys>?)

    @Query("SELECT * FROM apartment_remote_keys WHERE id = :id")
    suspend fun remoteKeysId(id: Long?): ApartmentRemoteKeys?

    @Query("DELETE FROM apartment_remote_keys")
    suspend fun clearRemoteKeys()
}