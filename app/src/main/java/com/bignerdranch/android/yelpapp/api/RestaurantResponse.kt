package com.bignerdranch.android.yelpapp.api

import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.google.gson.annotations.SerializedName

class RestaurantResponse {
    @SerializedName("businesses")
    lateinit var restaurants: List<YelpRestaurant>
//    @SerializedName("businesses")
//    lateinit var restaurant: YelpRestaurant
}