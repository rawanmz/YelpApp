package com.bignerdranch.android.yelpapp.data

import com.google.gson.annotations.SerializedName


data class YelpSearchResult(
    @SerializedName("total") val total: Int,
    @SerializedName("businesses") val restaurants: List<YelpRestaurant>
)