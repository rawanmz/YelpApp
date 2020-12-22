package com.bignerdranch.android.yelpapp.repository

import com.bignerdranch.android.yelpapp.api.WeatherApi
import com.bignerdranch.android.yelpapp.data.Weather
import com.bignerdranch.android.yelpapp.database.WeatherDao
import com.bignerdranch.android.yelpapp.database.YelpDao


class WeatherRepo(private val weatherApi: WeatherApi){

    suspend fun searchtWeather(key: String , q : String , days : String): Weather {
        val weather=weatherApi.searchWeather(key,q,days)
        return weather

    }
//    suspend fun addWather(weather: Weather) {
//        weatherDao.addWeatherData(weather)
//    }
//    suspend fun deleteWeather(){
//        weatherDao.deleteWeather()
//    }
//    suspend fun readAllWeatherData(weatherId:String):Weather{
//        return weatherDao.readAllWeatherData(weatherId)
//    }
}

