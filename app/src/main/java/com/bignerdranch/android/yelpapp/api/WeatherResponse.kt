package com.bignerdranch.android.yelpapp.api

import com.bignerdranch.android.yelpapp.data.Weather
import com.google.gson.annotations.SerializedName

class WeatherResponse {
    @SerializedName("current")
    lateinit var weather: Weather
    lateinit var forecast:ForcastDayResponse

}