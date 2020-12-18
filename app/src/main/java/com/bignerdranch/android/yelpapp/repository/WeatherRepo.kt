package com.bignerdranch.android.yelpapp.repository

import com.bignerdranch.android.yelpapp.api.WeatherApi
import com.bignerdranch.android.yelpapp.api.YelpApi
import com.bignerdranch.android.yelpapp.data.Forcast
import com.bignerdranch.android.yelpapp.data.Weather
import com.bignerdranch.android.yelpapp.data.YelpRestaurant

class WeatherRepo(private val weatherApi: WeatherApi){
    suspend fun searchWeather(
        key: String,
        q: String,
    ):Weather {
        return weatherApi.searchWeather(key, q).weather
    }
    suspend fun searchForecastWeather(key: String , q : String , days : String): List<Forcast> {
        return weatherApi.searchForcastWeather(key,q,days).forecast.forecastday[0].hour
    }
}

