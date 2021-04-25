package com.decagon.facilitymanagementapp_group_two.model.data.database.remotekeys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decagon.facilitymanagementapp_group_two.model.data.entities.ApplianceRemoteKeys
import com.decagon.facilitymanagementapp_group_two.model.data.entities.ComplainRemoteKeys

@Dao
interface ApplianceRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<ApplianceRemoteKeys>?)

    @Query("SELECT * FROM appliance_remote_keys WHERE id = :id")
    suspend fun remoteKeysId(id: Long?): ApplianceRemoteKeys?

    @Query("DELETE FROM appliance_remote_keys")
    suspend fun clearRemoteKeys()
}