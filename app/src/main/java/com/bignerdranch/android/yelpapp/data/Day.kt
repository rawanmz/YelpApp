package com.bignerdranch.android.yelpapp.data

data class Day(
    val avgtemp_c: Double,
    val avgtemp_f: Double,
    val condition: Condition,
    val daily_chance_of_rain: String,
    )