package com.bignerdranch.android.yelpapp.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.bignerdranch.android.yelpapp.data.Coordinates
import com.bignerdranch.android.yelpapp.data.YelpCategory
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
class DayPlanDaoTest {
    @get:Rule
    var instantTaskExcutiveRule= InstantTaskExecutorRule()
    private lateinit var database: YelpDatabase
    private lateinit var dao: DayPlanDao
    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                YelpDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.dayPlanDao()
    }
    @After
    fun teardown() {
        database.close()
    }
    @Test
    fun addPlan()= runBlockingTest{
        val Place= DayPlan("id","name",4.5,
                "123344455",false,22,4.3,"url",
                listOf(YelpCategory("title")), Coordinates(0.0,0.0),"description")

        dao.addDayPlanData(Place)
        val allData=dao.readDayPlanAllData().getOrAwaitValue()
        assertThat(allData.contains(Place))

    }
    @Test
    fun deletePlan()= runBlockingTest {
        val Place= DayPlan("id","name",4.5,
                "123344455",false,22,4.3,"url",
                listOf(YelpCategory("title")), Coordinates(0.0,0.0),"description")
        dao.addDayPlanData(Place)
        dao.deletePlan(Place)
        val allData=dao.readDayPlanAllData().getOrAwaitValue()
        assertThat(allData).doesNotContain(Place)
    }
}