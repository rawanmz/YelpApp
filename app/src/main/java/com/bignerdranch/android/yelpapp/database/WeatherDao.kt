package com.bignerdranch.android.yelpapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bignerdranch.android.yelpapp.data.Weather

@Dao
interface WeatherDao {
//
//    @Query("SELECT * FROM weather_table WHERE myWatherId = :valudId")
//    fun readAllWeatherData(valudId: String): Weather
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addWeatherData(vararg weather: Weather)
//
//    @Query("DELETE FROM weather_table")
//    suspend fun deleteWeather()
}