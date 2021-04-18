package com.decagon.facilitymanagementapp_group_two.model.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.decagon.facilitymanagementapp_group_two.model.data.entities.User
import com.decagon.facilitymanagementapp_group_two.model.data.entities.UserData

/**
 * DAO for interacting with the Request table in the database
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserData)

//    @Update
//    fun update(user: User)

    @Query("SELECT * FROM Users")
    fun get(): LiveData<UserData>

//    @Query("SELECT * FROM Users ORDER BY userId DESC LIMIT 1")
//    fun getLatestUser(): User
//
//    @Query("DELETE FROM Users")
//    fun clear()
//
//    @Query("Select * from Users Order by userId desc")
//    fun getAllUsers(): LiveData<List<User>>
}
