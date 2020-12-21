package com.bignerdranch.android.yelpapp.api

import com.bignerdranch.android.yelpapp.data.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast.json")
    suspend fun searchWeather(
        @Query("key") authHeader: String,
        @Query("q") q: String,
        @Query("days") days: String
    ): Weather
}