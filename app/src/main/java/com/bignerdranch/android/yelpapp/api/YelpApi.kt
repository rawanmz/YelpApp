package com.bignerdranch.android.yelpapp.api


import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YelpApi {
    @GET("businesses/search")
    suspend fun searchRestaurants(
        @Header("Authorization") authHeader: String,
        @Query("term") searchTerm: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude:String
    ): RestaurantResponse

    @GET("businesses/{id}")
    suspend fun detailRestaurants(
        @Header("Authorization") authHeader: String,
        @Query("id") searchTerm: String,
    ): RestaurantResponse
}
