package com.decagon.facilitymanagementapp_group_two.data.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.decagon.facilitymanagementapp_group_two.data.entities.Comments
import com.decagon.facilitymanagementapp_group_two.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.data.entities.User
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CentralDatabaseTest {

    private lateinit var commentDao: CommentsDao
    private lateinit var requestDao: RequestDao
    private lateinit var userDao: UserDao
    private lateinit var database: CentralDatabase

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.

        database = Room.inMemoryDatabaseBuilder(context, CentralDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        commentDao = database.commentDao
        requestDao = database.requestDao
        userDao = database.userDao
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

    @Test
    @Throws(java.lang.Exception::class)
    fun insertAndGetRequest() {
        val request = Request(1, "Food", "Akpabio", "Oily and Tasteless meal",
            "The food was oily and tasteless", "27:03:2021", null)
        requestDao.insert(request)
        val request2 = requestDao.getLatestRequest()
        assertEquals(request2.feedCategory, "Food")
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun insertAndGetUser() {
        val user = User("Olayinka", "olayinka@gattegs.jpg", "luvme.luvme@gmail.com",
            "07017278917", "Students", "Java", "casamento#1..090")
       userDao.insert(user)
        val user2 = userDao.getLatestUser()
        assertEquals(user2.role, "Students")
    }
}