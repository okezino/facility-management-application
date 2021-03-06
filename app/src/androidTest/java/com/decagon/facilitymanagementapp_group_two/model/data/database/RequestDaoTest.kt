package com.decagon.facilitymanagementapp_group_two.model.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.decagon.facilitymanagementapp_group_two.getOrAwaitValue
import com.decagon.facilitymanagementapp_group_two.model.data.database.dao.RequestDao
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import javax.inject.Inject
import javax.inject.Named


@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class RequestDaoTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    // Executes each task synchronously
    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test-db")
    lateinit var database: CentralDatabase
    private lateinit var requestDao: RequestDao

    @Before
    fun setup() {
        hiltRule.inject()
        requestDao = database.requestDao
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertNewRequest() = runBlockingTest {

        val request = Request("Water Issues",null, userId = "12345", time = "")
        requestDao.insert(request)
        val requests = requestDao.getAllRequest().getOrAwaitValue()
        assertThat(requests).contains(request)
    }
}
