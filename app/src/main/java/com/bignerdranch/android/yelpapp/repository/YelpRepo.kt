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
        resDao.deleteRestaurant()
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
                it.coordinates
            )
        }.toTypedArray()
        )
        return restaurant
    }
    suspend fun deletePlace(){
        resDao.deleteRestaurant()
    }
    }
