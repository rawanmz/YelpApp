package com.bignerdranch.android.yelpapp.repository

import androidx.lifecycle.LiveData
import com.bignerdranch.android.yelpapp.api.YelpApi
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.bignerdranch.android.yelpapp.database.YelpDao

class YelpRepo(private val yelpApi: YelpApi, private val resDao: YelpDao) {
    val readAllData: LiveData<List<YelpRestaurant>> =resDao.readAllData()
    suspend fun searchRestaurants(
        auth: String,
        term: String,
        lat: String,
        lon:String
    ): List<YelpRestaurant> {
        val restaurant=yelpApi.searchRestaurants(auth, term, lat,lon).restaurants
        resDao.addData(*restaurant.map {
            YelpRestaurant(
                0,
                it.name,
                it.rating,
                it.numReviews,
                it.distanceInMeters,
                it.imageUrl,
                it.categories,
                    )
        }.toTypedArray()
        )
        return restaurant
    }
    suspend fun searchLocation(auth: String, lat: String, lon: String): List<YelpRestaurant> {
        return yelpApi.searchLocation(auth, lat, lon).restaurants
    }
    suspend fun addData(data: YelpRestaurant) {
        resDao.addData(data)
    }
}