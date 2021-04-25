package com.decagon.facilitymanagementapp_group_two.model.data.database.remotekeys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decagon.facilitymanagementapp_group_two.model.data.entities.ComplainRemoteKeys
import com.decagon.facilitymanagementapp_group_two.model.data.entities.RemoteKeys

@Dao
interface ComplainRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<ComplainRemoteKeys>?)

    @Query("SELECT * FROM complain_remote_keys WHERE id = :id")
    suspend fun remoteKeysId(id: Long?): ComplainRemoteKeys?

    @Query("DELETE FROM complain_remote_keys")
    suspend fun clearRemoteKeys()
}
