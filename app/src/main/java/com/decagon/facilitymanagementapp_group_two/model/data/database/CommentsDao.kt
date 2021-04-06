package com.decagon.facilitymanagementapp_group_two.model.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Comments

/**
 * DAO for interacting with the Comments table in the database
 */
@Dao
interface CommentsDao {

    @Insert
    fun insert(comment: Comments)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(comments: List<Comments>)

    @Update
    fun update(comment: Comments)

    @Query("SELECT * from Comments WHERE commentId = :key")
    fun get(key: Long): List<Comments>?

    @Query("SELECT * FROM Comments ORDER BY requestId DESC LIMIT 1")
    fun getLatestComment(): Comments

    @Query("DELETE FROM Comments")
    fun clear()

    @Query("Select * from Comments where commentId = :key Order by requestId desc")
    fun getAllComments(key: Long?): LiveData<List<Comments>>
}
