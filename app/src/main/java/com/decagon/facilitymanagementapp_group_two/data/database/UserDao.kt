package com.decagon.facilitymanagementapp_group_two.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.decagon.facilitymanagementapp_group_two.data.entities.User

/**
 * DAO for interacting with the Request table in the database
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * from Users WHERE userId = :key")
    fun get(key: Long): User

    @Query("SELECT * FROM Users ORDER BY userId DESC LIMIT 1")
    fun getLatestUser(): User

    @Query("DELETE FROM Users")
    fun clear()

    @Query("Select * from Users Order by userId desc")
    fun getAllUsers(): LiveData<List<User>>
}
