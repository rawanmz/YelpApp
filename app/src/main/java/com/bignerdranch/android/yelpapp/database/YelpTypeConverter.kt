package com.bignerdranch.android.yelpapp.database

import androidx.room.TypeConverter
import com.bignerdranch.android.yelpapp.data.YelpCategory
import com.bignerdranch.android.yelpapp.data.Coordinates
import com.bignerdranch.android.yelpapp.data.Weather
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
    fun fromweatherCondition(weatherCondition: Weather.Current):String{
        return "${weatherCondition.temp_c}, ${weatherCondition.condition.text},${weatherCondition.condition.icon}"
    }
    @TypeConverter
    fun toweatherCondition(condition :String): Weather.Current {
        val x = condition.split(",")
        return  Weather.Current(Weather.Current.Condition(x[1],x[2]),x[0].toDouble())
    }

    @TypeConverter
    fun toRegion(loc:String): Coordinates {
        val type =loc.split(",")
        val location=Coordinates(type[0].toDouble(),type[1].toDouble())
          return location
    }

    @TypeConverter
    fun fromRegion(value: Coordinates): String {
        val location = "${value.latitude},${value.longitude} "
        return location
    }
}
