package com.bignerdranch.android.yelpapp.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.bignerdranch.android.yelpapp.data.Coordinates
import com.bignerdranch.android.yelpapp.data.YelpCategory
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.bignerdranch.android.yelpapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class YelpDaoTest{
    @get:Rule
    var instantTaskExcutiveRule= InstantTaskExecutorRule()
    private lateinit var database: YelpDatabase
    private lateinit var dao: YelpDao
    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            YelpDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.dao()
    }
    @After
    fun teardown() {
        database.close()
    }
    @Test
    fun addPlcae()= runBlockingTest{
        val Place= YelpRestaurant("id","name",4.5,
            "123344455",22,4.3,"url",
            listOf(YelpCategory("title")), Coordinates(0.0,0.0))

        dao.addData(Place)
        val allPhotos=dao.readAllData().getOrAwaitValue()
        assertThat(allPhotos).contains(Place)

    }
    @Test
    fun deletePhoto()= runBlockingTest {
        val Place= YelpRestaurant("id","name",4.5,
            "123344455",22,4.3,"url",
            listOf(YelpCategory("title")), Coordinates(0.0,0.0))
        dao.addData(Place)
        dao.deleteRestaurant()
        val allPhoto=dao.readAllData().getOrAwaitValue()
        assertThat(allPhoto).doesNotContain(Place)
    }
    @Test
    fun searchPlace()= runBlockingTest {
        val Place= YelpRestaurant("id","name",4.5,
            "123344455",22,4.3,"url",
            listOf(YelpCategory("title")), Coordinates(0.0,0.0))
        dao.addData(Place)
        dao.searchResturantById(Place.yelpId)
        val allPhoto=dao.readAllData().getOrAwaitValue()
        assertThat(allPhoto).contains(Place)
    }
}