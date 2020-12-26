package com.bignerdranch.android.yelpapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bignerdranch.android.yelpapp.data.Weather
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
@Database(entities = [YelpRestaurant::class,DayPlan::class] ,
    version = 1)
@TypeConverters(YelpTypeConverter::class)

abstract class YelpDatabase: RoomDatabase(){
    abstract fun dao() : YelpDao
    abstract fun dayPlanDao() : DayPlanDao
   // abstract fun weatherDao() : WeatherDao

}