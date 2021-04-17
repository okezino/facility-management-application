package com.decagon.facilitymanagementapp_group_two.model.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Comments
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.model.data.entities.User
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class CentralDatabaseTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    // Executes each task synchronously
    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test-db")
    lateinit var database: CentralDatabase
    private lateinit var commentDao: CommentsDao
    private lateinit var requestDao: RequestDao
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        hiltRule.inject()
        commentDao = database.commentDao
        userDao = database.userDao
        requestDao = database.requestDao
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetComment() {
        val com1 = Comments(1, "loveworld", "I really enjoyed the meal", "tricia@pat.png", "08:20:16")
        val com2 = Comments(2, "Otega", "The AC in my room is faulty", "otega@gattegs.jpg", "22:09:01")
        val sumCommment = listOf(com1, com2)
        commentDao.insertAll(sumCommment)
        val comment2 = commentDao.getLatestComment()
        assertEquals(comment2.userName, "Otega")
    }




}
