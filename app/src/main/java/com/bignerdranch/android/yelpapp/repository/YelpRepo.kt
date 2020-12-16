package com.bignerdranch.android.yelpapp.repository

import com.bignerdranch.android.yelpapp.api.YelpApi
import com.bignerdranch.android.yelpapp.data.YelpRestaurant

class YelpRepo (private val yelpApi: YelpApi ) {

    suspend fun searchRestaurants(auth:String,term: String,location:String): List<YelpRestaurant> {
        return yelpApi.searchRestaurants(auth,term,location).restaurants
    }
    suspend fun searchLocation(auth:String,lat: String,lon:String): List<YelpRestaurant> {
        return yelpApi.searchLocation(auth,lat,lon).restaurants
}
    }