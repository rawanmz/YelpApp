package com.bignerdranch.android.yelpapp.data

data class Forecastday(
    val date: String,
    val day: Day,
    val hour: List<Hour>
)