package com.bignerdranch.android.yelpapp.database

import androidx.room.TypeConverter
import com.bignerdranch.android.yelpapp.data.YelpCategory
import com.bignerdranch.android.yelpapp.data.YelpLocation
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class YelpTypeConverter {

@TypeConverter
fun fromList(title: List<YelpCategory>): String {
    val gson = Gson()
    val type = object : TypeToken<List<YelpCategory>>() {}.type
    return gson.toJson(title, type)
}
    @TypeConverter
    fun toList(title: String): List<YelpCategory> {
        val gson = Gson()
        val type = object : TypeToken<List<YelpCategory>>() {}.type
        return gson.fromJson(title, type)
    }
}