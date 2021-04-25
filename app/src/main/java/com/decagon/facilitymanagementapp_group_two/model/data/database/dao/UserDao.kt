package com.decagon.facilitymanagementapp_group_two.model.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.decagon.facilitymanagementapp_group_two.model.data.entities.UserData

/**
 * DAO for interacting with the Request table in the database
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserData)

    @Update
    fun update(user: UserData)

    @Query("SELECT * FROM Users")
    fun get(): LiveData<UserData>
}
