package com.bignerdranch.android.yelpapp.repository

import com.bignerdranch.android.yelpapp.api.WeatherApi
import com.bignerdranch.android.yelpapp.data.Weather


class WeatherRepo(private val weatherApi: WeatherApi):WeatherRepoInterface{

    override suspend fun searchWeather(key: String, q : String, days : String): Weather {
        val weather=weatherApi.searchForcastWeather(key,q,days)
        return weather

    }
}

