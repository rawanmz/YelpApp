package com.bignerdranch.android.yelpapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
@Database(entities = [YelpRestaurant::class] ,  version = 1,exportSchema = false)
@TypeConverters(YelpTypeConverter::class)

abstract class YelpDatabase: RoomDatabase(){
    abstract fun dao() : YelpDao
}