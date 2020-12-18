package com.bignerdranch.android.yelpapp.data

import com.google.gson.annotations.SerializedName

data class Weather(
    val temp_c: Double,
    val condition: Condition,
)
