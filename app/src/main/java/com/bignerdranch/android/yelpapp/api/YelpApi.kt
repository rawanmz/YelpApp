package com.bignerdranch.android.yelpapp.api


import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YelpApi {
    @GET("businesses/search")
    suspend fun searchRestaurants(
        @Header("Authorization") authHeader: String,
        @Query("term") searchTerm: String,
        @Query("location") location: String) : RestaurantResponse
    @GET("businesses/search")
    suspend fun searchLocation(
        @Header("Authorization") authHeader: String,
        @Query("latitude") lat: String,
        @Query("longitude") lon: String) : RestaurantResponse
}
