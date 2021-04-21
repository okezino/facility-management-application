package com.decagon.facilitymanagementapp_group_two.model.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Feeds
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
class FeedDaoTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    // Executes each task synchronously
    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test-db")
    lateinit var database: CentralDatabase
    private lateinit var feedDao: FeedDao

    @Before
    fun setup() {
        hiltRule.inject()
        feedDao = database.feedDao
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertAllFeeds() = runBlockingTest {
        val feeds = listOf(Feeds("Food",1,"1234","Food"),Feeds("Health",2,"5678","Health"))
        feedDao.insertAllFeeds(feeds)

        val feedId = feedDao.getFeedId("Health")

        assertThat(feedId).matches("5678")
    }

}