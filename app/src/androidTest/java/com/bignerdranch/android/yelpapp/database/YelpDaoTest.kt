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
            "123344455",false,22,4.3,"url",
            listOf(YelpCategory("title")), Coordinates(0.0,0.0))

        dao.addData(Place)
        val allData=dao.readAllData().getOrAwaitValue()
        assertThat(allData).contains(Place)

    }
    @Test
    fun deletePlace()= runBlockingTest {
        val Place= YelpRestaurant("id","name",4.5,
            "123344455",false,22,4.3,"url",
            listOf(YelpCategory("title")), Coordinates(0.0,0.0))
        dao.addData(Place)
        dao.deleteRestaurant()
        val allData=dao.readAllData().getOrAwaitValue()
        assertThat(allData).doesNotContain(Place)
    }
    @Test
    fun searchPlace()= runBlockingTest {
        val Place= YelpRestaurant("id","name",4.5,
            "123344455",false,22,4.3,"url",
            listOf(YelpCategory("title")), Coordinates(0.0,0.0))
        dao.addData(Place)
        dao.searchResturantById(Place.yelpId)
        val allData=dao.readAllData().getOrAwaitValue()
        assertThat(allData).contains(Place)
    }
}

//
//
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.room.Room
//import androidx.test.core.app.ApplicationProvider
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.filters.SmallTest
//import com.bignerdranch.android.yelpapp.data.Coordinates
//import com.bignerdranch.android.yelpapp.data.YelpCategory
//import com.bignerdranch.android.yelpapp.data.YelpRestaurant
//import com.bignerdranch.android.yelpapp.getOrAwaitValue
//
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import com.google.common.truth.Truth.assertThat
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runBlockingTest
//import org.junit.Rule
//@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class)
//@SmallTest
//class RestaurantDaoTest {
//    @get:Rule
//    var instantTaskExcutiveRule= InstantTaskExecutorRule()
//    private lateinit var database : YelpDatabase
//    private lateinit var dao : YelpDao
//    //make sure to have new db for every test case
//    @Before
//    fun setup(){
//        // to save in memory only (RAM) for test case not in the persist storage
//        database = Room.inMemoryDatabaseBuilder(
//            //get reference of context inside test case
//            ApplicationProvider.getApplicationContext(),
//            YelpDatabase::class.java
//        ).allowMainThreadQueries().build() // to run the test on single thread (main) ->
//        // action execute one after another
//        dao = database.dao()
//    }
//    @After
//    fun teardown (){
//        database.close()
//    }
//    // run blocking to execute coroutine in the main thread
//    @Test
//    fun addRestaurant ()= runBlockingTest {
//        // insert fake photo object to database for testing
//        val Place= YelpRestaurant("id","name",4.5,
//            "123344455",false,22,4.3,"url",
//            listOf(YelpCategory("title")), Coordinates(0.0,0.0))
//        dao.addData(Place)
//        //check if the photo in the list ( database)
//        // call function on livedata object to get the value
//        val allPlaces = dao.readAllData().getOrAwaitValue()
//        //check if the photo in the list
//        assertThat(allPlaces).contains(Place)
//    }
////    @Test
////    fun deleteRestaurant() = runBlockingTest {
////        val place = Restaurant("id","Test",1.0,1,0.0,"image_url",
////            listOf(YelpCategory("")), Connection(0.0,0.0),"134567843","100",false,
////            YelpLocation("NewYork","hvyhjv"))
////        dao.addRestaurant(place)
////        dao.deleteRestaurant()
////        val allPlaces=dao.readAllRestaurant().getOrAwaitValue()
////        assertThat(allPlaces).doesNotContain(place)
////    }
//}