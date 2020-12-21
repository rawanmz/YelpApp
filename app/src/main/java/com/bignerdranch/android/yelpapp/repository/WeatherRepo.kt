package com.bignerdranch.android.yelpapp.repository

import com.bignerdranch.android.yelpapp.api.WeatherApi
import com.bignerdranch.android.yelpapp.data.Weather


class WeatherRepo(private val weatherApi: WeatherApi){

    suspend fun searchtWeather(key: String , q : String , days : String): Weather {
        return weatherApi.searchWeather(key,q,days)

    }
}

