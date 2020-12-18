package com.bignerdranch.android.yelpapp.api

import com.bignerdranch.android.yelpapp.data.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("current.json")
    suspend fun searchWeather(
        @Query("key") authHeader: String,
        @Query("q") lat: String
        ): WeatherResponse

    @GET("forecast.json")
    suspend fun searchForcastWeather(
        @Query("key") authHeader: String,
        @Query("q") query: String,
        @Query("day") day: String
    ): WeatherResponse
}