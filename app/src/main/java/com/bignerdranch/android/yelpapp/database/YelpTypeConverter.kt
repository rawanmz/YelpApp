package com.bignerdranch.android.yelpapp.database

import androidx.room.TypeConverter
import com.bignerdranch.android.yelpapp.data.YelpCategory
import com.bignerdranch.android.yelpapp.data.Coordinates
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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

    @TypeConverter
    fun fromRegion(value: Coordinates): String {
        val location = "${value.latitude},${value.longitude} "
        return location
        }

    @TypeConverter
    fun toRegion(loc:String): Coordinates {
        val type =loc.split(",")
        val location=Coordinates(type[0].toDouble(),type[1].toDouble())
          return location
    }
}
