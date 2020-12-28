package com.bignerdranch.android.yelpapp.repository

import com.bignerdranch.android.yelpapp.data.Weather

interface WeatherRepoInterface {

    suspend fun searchWeather(key: String, q: String, days: String): Weather
}