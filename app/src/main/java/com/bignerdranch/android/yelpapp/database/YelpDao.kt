package com.bignerdranch.android.yelpapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bignerdranch.android.yelpapp.data.YelpRestaurant

@Dao
interface YelpDao {
    @Query("SELECT * FROM restaurant_table ORDER BY myid ASC ")
    fun readAllData (): LiveData<List<YelpRestaurant>>

    @Insert
    suspend fun addData (vararg business: YelpRestaurant)

    @Query("DELETE FROM restaurant_table")
    suspend fun deleteRestaurant()
}