package com.decagon.facilitymanagementapp_group_two.model.data.database.remotekeys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decagon.facilitymanagementapp_group_two.model.data.entities.ComplainRemoteKeys
import com.decagon.facilitymanagementapp_group_two.model.data.entities.OthersRemoteKeys

@Dao
interface OthersRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<OthersRemoteKeys>?)

    @Query("SELECT * FROM others_remote_keys WHERE id = :id")
    suspend fun remoteKeysId(id: Long?): OthersRemoteKeys?

    @Query("DELETE FROM others_remote_keys")
    suspend fun clearRemoteKeys()
}